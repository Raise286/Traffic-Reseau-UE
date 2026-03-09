package com.travel.central.controller;
import com.travel.central.domain.dto.ReservationEventDto;
import com.travel.central.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/events")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> postReservationEvent(@Valid @RequestBody ReservationEventDto event) {
        // On retourne Mono<Void> car on ne renvoie pas de contenu, juste 200 OK si succès
        return reservationService.processReservationEvent(event);
    }
}