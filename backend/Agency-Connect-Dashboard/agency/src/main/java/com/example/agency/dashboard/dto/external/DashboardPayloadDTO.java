package com.example.agency.dashboard.dto.external;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;

@Data
@Builder
public class DashboardPayloadDTO {
    private String clientNom;
    private String clientPrenom;
    private String destination;
    private LocalDate dateDepart;
    private int nombrePersonnes;
    private String typeVoyage;
    private String commentaires;
}