package com.travel.central.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.travel.central.domain.TripType;
// Structure EXACTE demandée pour le POST vers le backend externe
public record ReservationEventDto(
        @NotBlank String nomClient,
        @NotBlank String prenomClient,
        @Email @NotBlank String emailClient,
        String telephoneClient,
        String paysOrigineClient,
        String destinationSouhaiteeClient,
        @NotBlank String dateDepartClient,
        @Positive @NotNull Integer nombrePersonnes,
        @NotNull TripType typeVoyage, // AFFAIRES | TOURISME | ETUDE
        String commentaires,
        @NotBlank String nomEvent // "RESERVATION_CREATED"
) {}