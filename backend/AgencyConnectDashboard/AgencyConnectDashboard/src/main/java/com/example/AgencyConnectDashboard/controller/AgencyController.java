package com.example.AgencyConnectDashboard.controller;

import com.example.AgencyConnectDashboard.dashboard.domain.Reservation;
import com.example.AgencyConnectDashboard.dashboard.dto.EventRequestDTO;
import com.example.AgencyConnectDashboard.dashboard.dto.NotificationResponseDTO;
import com.example.AgencyConnectDashboard.service.AgencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
@RequiredArgsConstructor
public class AgencyController {

    private final AgencyService agencyService;

    // 1. Recevoir un événement
    @PostMapping("/events")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<NotificationResponseDTO> receiveEvent(@RequestBody EventRequestDTO eventRequest) {
        return agencyService.createNotificationFromEvent(eventRequest);
    }

    // 2. Traiter une notification
    @PutMapping("/notifications/{id}/process")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> processNotification(@PathVariable String id) {
        return agencyService.processNotification(id);
    }

    // 3. Liste des notifications
    @GetMapping("/notifications")
    public Flux<NotificationResponseDTO> getNotifications() {
        return agencyService.getAllNotifications();
    }

    // 4. Liste des réservations
    @GetMapping("/reservations")
    public Flux<Reservation> getReservations() {
        return agencyService.getAllReservations();
    }
}