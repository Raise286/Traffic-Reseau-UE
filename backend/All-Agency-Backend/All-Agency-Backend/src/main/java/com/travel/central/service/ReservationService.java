package com.travel.central.service;
import com.travel.central.domain.dto.ReservationEventDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final WebClient agencyDashboardClient;

    /**
     * Reçoit l'événement de réservation et le transmet (proxy)
     * au backend externe SANS persistance locale.
     */
    public Mono<Void> processReservationEvent(ReservationEventDto event) {
        log.info("Reception événement réservation pour: {}. Transmission au dashboard...", event.emailClient());

        return agencyDashboardClient.post()
                // L'URL de base est déjà configurée dans WebClientConfig
                .bodyValue(event)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(v -> log.info("Événement transmis avec succès au dashboard."))
                .doOnError(e -> log.error("Erreur lors de la transmission de l'événement: {}", e.getMessage()));
    }
}