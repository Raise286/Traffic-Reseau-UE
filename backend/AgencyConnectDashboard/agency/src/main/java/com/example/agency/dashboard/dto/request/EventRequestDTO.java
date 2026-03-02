package com.example.agency.dashboard.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventRequestDTO {
    @NotBlank(message = "Le nom est obligatoire")
    private String nomClient;

    @NotBlank(message = "Le prénom est obligatoire")
    private String prenomClient;

    @Email(message = "Email invalide")
    @NotBlank
    private String emailClient;

    private String telephoneClient;
    private String paysOrigineClient;

    @NotBlank
    private String destinationSouhaiteeClient;

    @NotNull
    private LocalDate dateDepartClient;

    @Min(1)
    private int nombrePersonnes;

    private String typeVoyage;
    private String commentaires;

    @NotBlank(message = "Le nom de l'événement est requis")
    private String nomEvent;
}