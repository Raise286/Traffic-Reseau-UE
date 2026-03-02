package com.example.agency.service;

import com.example.agency.dashboard.dto.external.DashboardPayloadDTO;
import com.example.agency.dashboard.dto.external.DashboardPayloadDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class AgencyClient {

    private final WebClient webClient;

    public AgencyClient(WebClient.Builder webClientBuilder,
                        @Value("${external.agency-dashboard.base-url}") String baseUrl) {
        this.webClient = webClientBuilder.baseUrl(baseUrl).build();
    }

    @CircuitBreaker(name = "dashboardService")
    @Retry(name = "dashboardService")
    public Mono<String> sendNotification(DashboardPayloadDTO payload) {
        log.info("Sending notification to Dashboard API for client: {}", payload.getClientNom());

        return webClient.post()
                .uri("/api/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class)
                .doOnSuccess(r -> log.info("Notification sent successfully"));
    }
}