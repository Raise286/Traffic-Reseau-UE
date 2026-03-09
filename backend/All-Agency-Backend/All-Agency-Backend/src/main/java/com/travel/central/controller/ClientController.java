package com.travel.central.controller;
import com.travel.central.domain.dto.ClientDto;
import com.travel.central.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/save-client")
    public Mono<ResponseEntity<ClientDto>> registerClient(@Valid @RequestBody ClientDto dto) {
        return clientService.registerClient(dto)
                .map(ResponseEntity::ok);
    }
}