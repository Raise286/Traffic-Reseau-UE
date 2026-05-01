package com.example.AgencyConnectDashboard.service;

import com.example.AgencyConnectDashboard.dashboard.domain.Notification;
import com.example.AgencyConnectDashboard.dashboard.domain.NotificationStatus;
import com.example.AgencyConnectDashboard.dashboard.domain.Reservation;
import com.example.AgencyConnectDashboard.dashboard.dto.EventRequestDTO;
import com.example.AgencyConnectDashboard.dashboard.dto.NotificationPayloadDTO;
import com.example.AgencyConnectDashboard.dashboard.dto.NotificationResponseDTO;
import com.example.AgencyConnectDashboard.dashboard.repository.NotificationRepository;
import com.example.AgencyConnectDashboard.dashboard.repository.ReservationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgencyService {

    private final NotificationRepository notificationRepository;
    private final ReservationRepository reservationRepository;
    private final ObjectMapper objectMapper;

    // ----------------------------------------------------------------
    // 1. RECEPTION ET CREATION DE NOTIFICATION
    // ----------------------------------------------------------------
    public Mono<NotificationResponseDTO> createNotificationFromEvent(EventRequestDTO event) {
        // Mapping Event -> Notification Payload
        NotificationPayloadDTO payload = NotificationPayloadDTO.builder()
                .clientNom(event.getNomClient())
                .clientPrenom(event.getPrenomClient())
                .destination(event.getDestinationSouhaiteeClient())
                .dateDepart(event.getDateDepartClient())
                .nombrePersonnes(event.getNombrePersonnes())
                .typeVoyage(event.getTypeVoyage())
                .commentaires(event.getCommentaires())
                .build();

        String payloadJson;
        try {
            payloadJson = objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            return Mono.error(new RuntimeException("Error serializing payload", e));
        }

        Notification notification = Notification.builder()
                .id(UUID.randomUUID().toString())
                .eventName(event.getNomEvent())
                .payloadJson(payloadJson)
                .status(NotificationStatus.RECEIVED)
                .receivedAt(LocalDateTime.now())
                .isNew(true)  // ✅ IMPORTANT : marquer explicitement comme nouvelle
                .build();

        return notificationRepository.save(notification)
                .doOnSuccess(n -> {
                    n.markAsNotNew();  // Marquer comme persistée
                    log.info("✅ Notification créée avec ID: {}", n.getId());
                })
                .doOnError(e -> log.error("❌ Erreur création notification: {}", e.getMessage()))
                .map(this::mapToResponseDTO);
    }

    // ----------------------------------------------------------------
    // 2. TRAITEMENT DE NOTIFICATION (PROCESS)
    // ----------------------------------------------------------------
    @Transactional
    public Mono<Void> processNotification(String id) {
        return notificationRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Notification not found")))
                .flatMap(notification -> {
                    if (notification.getStatus() == NotificationStatus.PROCESSED) {
                        return Mono.error(new RuntimeException("Notification already processed"));
                    }

                    NotificationPayloadDTO payload;
                    try {
                        payload = objectMapper.readValue(notification.getPayloadJson(), NotificationPayloadDTO.class);
                    } catch (JsonProcessingException e) {
                        return Mono.error(new RuntimeException("Error deserializing payload", e));
                    }

                    Reservation reservation = Reservation.builder()
                            .clientNom(payload.getClientNom())
                            .clientPrenom(payload.getClientPrenom())
                            .destination(payload.getDestination())
                            .dateDepart(payload.getDateDepart()) // ✅ Garder comme String
                            .nombrePersonnes(payload.getNombrePersonnes())
                            .typeVoyage(mapTypeVoyage(payload.getTypeVoyage()))
                            .commentaires(payload.getCommentaires())
                            .createdAt(LocalDateTime.now())
                            .source("API")
                            .build();

                    notification.setStatus(NotificationStatus.PROCESSED);

                    notification.markAsNotNew();

                    return reservationRepository.save(reservation)
                            .flatMap(savedReservation -> {

                                return notificationRepository.save(notification);
                            })
                            .then();
                });
    }

    // ----------------------------------------------------------------
    // 3. CONSULTATION
    // ----------------------------------------------------------------
    public Flux<NotificationResponseDTO> getAllNotifications() {
        return notificationRepository.findAll()
                .map(this::mapToResponseDTO);
    }

    public Flux<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    // ----------------------------------------------------------------
    // UTILITAIRES DE MAPPING
    // ----------------------------------------------------------------
    private NotificationResponseDTO mapToResponseDTO(Notification notification) {
        NotificationPayloadDTO payload = null;
        try {
            payload = objectMapper.readValue(notification.getPayloadJson(), NotificationPayloadDTO.class);
        } catch (JsonProcessingException e) {
            log.error("Error reading payload json for notification {}", notification.getId());
        }

        return NotificationResponseDTO.builder()
                .id(notification.getId())
                .eventName(notification.getEventName())
                .payload(payload)
                .status(notification.getStatus())
                .receivedAt(notification.getReceivedAt())
                .build();
    }

    /**
     * Logique de mapping pour le Type de Voyage.
     * Entrée (Event): AFFAIRES | TOURISME | ETUDE
     * Sortie (Reservation): LEISURE | BUSINESS | HONEYMOON
     */
    private String mapTypeVoyage(String inputType) {
        if (inputType == null) return "LEISURE";
        switch (inputType.toUpperCase()) {
            case "AFFAIRES":
                return "BUSINESS";
            case "TOURISME":
                return "LEISURE";
            case "ETUDE":
                return "LEISURE";
            default:
                return "LEISURE";
        }
    }
}