package com.example.agency.service;

import com.example.agency.dashboard.domain.Notification;
import com.example.agency.dashboard.domain.WebhookEvent;
import com.example.agency.dashboard.domain.WebhookEvent;
import com.example.agency.dashboard.dto.request.EventRequestDTO;
import com.example.agency.mapper.EventMapper;
import com.example.agency.dashboard.repository.NotificationRepository;
import com.example.agency.dashboard.repository.WebhookEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class WebhookService {

    private final WebhookEventRepository eventRepository;
    private final NotificationRepository notificationRepository;
    private final  com.example.agency.service.AgencyClient agencyClient;
    private final EventMapper eventMapper;
    private final TransactionalOperator transactionalOperator;

    /**
     * Point d'entrée principal.
     * Gère la transaction et le flux réactif complet.
     */
    public Mono<ResponseEntity<String>> processEvent(EventRequestDTO requestDTO) {
        WebhookEvent event = eventMapper.toEntity(requestDTO);

        // 1. Sauvegarde l'événement (Idempotence)
        return eventRepository.save(event)
                .flatMap(savedEvent -> {
                    log.info("Event saved with ID: {}", savedEvent.getId());
                    return processNotification(savedEvent, requestDTO);
                })
                .map(notif -> ResponseEntity.ok("Event processed successfully. Notification ID: " + notif.getId()))
                // Gestion explicite de l'idempotence (Doublon en base)
                .onErrorResume(DataIntegrityViolationException.class, e -> {
                    log.warn("Duplicate event detected (Idempotency Key: {}). Skipping.", event.getIdempotencyKey());
                    return Mono.just(ResponseEntity.ok("Event already processed (Idempotent)"));
                })
                .as(transactionalOperator::transactional); // Assure la transaction DB
    }

    private Mono<Notification> processNotification(WebhookEvent savedEvent, EventRequestDTO requestDTO) {
        Notification initialNotification = Notification.builder()
                .id(UUID.randomUUID())
                .eventId(savedEvent.getId())
                .status(Notification.Status.PENDING)
                .isNew(true)
                .build();

        // 2. Sauvegarde Notification (PENDING) -> Appel Externe -> Mise à jour Status
        return notificationRepository.save(initialNotification)
                .flatMap(notification -> {
                    // Appel externe (Retry & CB appliqués dans AgencyClient)
                    return agencyClient.sendNotification(eventMapper.toExternalDto(requestDTO))
                            .map(response -> {
                                notification.setStatus(Notification.Status.SENT);
                                return notification;
                            })
                            // En cas d'épuisement des retries ou Circuit Breaker Open
                            .onErrorResume(ex -> {
                                log.error("Failed to send notification: {}", ex.getMessage());
                                notification.setStatus(Notification.Status.FAILED);
                                notification.setLastError(ex.getMessage());
                                return Mono.just(notification);
                            });
                })
                // 3. Mise à jour finale en base
                .flatMap(notificationRepository::save);
    }
}