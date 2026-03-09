package com.travel.central.service;
import com.travel.central.domain.dto.ClientDto;
import com.travel.central.domain.model.Client;
import com.travel.central.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {

    private final ClientRepository clientRepository;

    public Mono<ClientDto> registerClient(ClientDto dto) {
        log.info("Enregistrement nouveau client: {}", dto.email());

        Client client = Client.builder()
                .nom(dto.nom())
                .prenom(dto.prenom())
                .email(dto.email())
                .telephone(dto.telephone())
                .paysOrigine(dto.paysOrigine())
                .destinationSouhaitee(dto.destinationSouhaitee())
                .build();

        return clientRepository.save(client)
                .map(saved -> new ClientDto(
                        saved.getId(),
                        saved.getNom(),
                        saved.getPrenom(),
                        saved.getEmail(),
                        saved.getTelephone(),
                        saved.getPaysOrigine(),
                        saved.getDestinationSouhaitee(),
                        "Client enregistré avec succès"
                ));
    }
}