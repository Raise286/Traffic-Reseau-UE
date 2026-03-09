package com.travel.central.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ClientDto(
        Long idClient, // Present only in response
        @NotBlank String nom,
        @NotBlank String prenom,
        @Email @NotBlank String email,
        String telephone,
        String paysOrigine,
        String destinationSouhaitee,
        String message // Present only in response
) {}