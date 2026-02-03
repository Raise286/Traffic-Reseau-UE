package com.travel.central.controller;


import com.travel.central.domain.dto.AgencyDto;
import com.travel.central.service.AgencyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/agency")
@RequiredArgsConstructor
public class AgencyController {

    private final AgencyService agencyService;

    @PostMapping("/create-agency")
    public Mono<ResponseEntity<AgencyDto>> registerAgency(@Valid @RequestBody AgencyDto dto) {
        return agencyService.registerAgency(dto)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/agencies")
    public Flux<AgencyDto> getAllAgencies() {
        return agencyService.getAllAgencies();
    }
}