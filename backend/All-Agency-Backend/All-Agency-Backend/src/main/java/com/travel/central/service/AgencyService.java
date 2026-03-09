package com.travel.central.service;
import com.travel.central.domain.dto.AgencyDto;
import com.travel.central.domain.model.Agency;
import com.travel.central.repository.AgencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgencyService {

    private final AgencyRepository agencyRepository;

    public Mono<AgencyDto> registerAgency(AgencyDto dto) {
        log.info("Enregistrement nouvelle agence: {}", dto.nom());

        Agency agency = Agency.builder()
                .nom(dto.nom())
                .localisation(dto.localisation())
                .description(dto.description())
                .image(dto.image())
                .callbackUrl(dto.callbackUrl())
                .build();

        return agencyRepository.save(agency)
                .map(this::mapToDto);
    }

    public Flux<AgencyDto> getAllAgencies() {
        return agencyRepository.findAll()
                .map(this::mapToDto);
    }

    private AgencyDto mapToDto(Agency entity) {
        return new AgencyDto(
                entity.getId(),
                entity.getNom(),
                entity.getLocalisation(),
                entity.getDescription(),
                entity.getImage(),
                entity.getCallbackUrl()
        );
    }
}