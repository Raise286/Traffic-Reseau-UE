package com.travel.central.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record AgencyDto(
        Long id,
        @NotBlank String nom,
        String localisation,
        String description,
        String image,
        String callbackUrl
) {}