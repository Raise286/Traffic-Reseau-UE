package com.example.agency.mapper;

import com.example.agency.dashboard.domain.WebhookEvent;
import com.example.agency.dashboard.dto.external.DashboardPayloadDTO;
import com.example.agency.dashboard.dto.request.EventRequestDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

@Component
public class EventMapper {

    private final ObjectMapper objectMapper;
    public EventMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public WebhookEvent toEntity(EventRequestDTO dto) {
        try {
            String rawJson = objectMapper.writeValueAsString(dto);
            return WebhookEvent.builder()
                    .id(UUID.randomUUID())
                    .eventName(dto.getNomEvent())
                    .payload(rawJson) // CHANGEMENT ICI : Plus de Json.of(), juste la string brute
                    .idempotencyKey(generateHash(rawJson))
                    .newEvent(true)
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erreur de mapping JSON", e);
        }
    }

    // ... Le reste du fichier (toExternalDto et generateHash) ne change pas ...
    public DashboardPayloadDTO toExternalDto(EventRequestDTO dto) {
        return DashboardPayloadDTO.builder()
                .clientNom(dto.getNomClient())
                .clientPrenom(dto.getPrenomClient())
                .destination(dto.getDestinationSouhaiteeClient())
                .dateDepart(dto.getDateDepartClient())
                .nombrePersonnes(dto.getNombrePersonnes())
                .typeVoyage(dto.getTypeVoyage())
                .commentaires(dto.getCommentaires())
                .build();
    }

    private String generateHash(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 non disponible", e);
        }
    }
}