package com.example.agency.controller;

import com.example.agency.dashboard.dto.request.EventRequestDTO;
import com.example.agency.service.WebhookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class WebhookController {

    private final WebhookService webhookService;

    @PostMapping
    public Mono<ResponseEntity<String>> receiveEvent(@Valid @RequestBody EventRequestDTO eventRequest) {
        return webhookService.processEvent(eventRequest);
    }
}