package com.example.AgencyConnectDashboard.dashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPayloadDTO {
    private String clientNom;
    private String clientPrenom;
    private String destination;
    private String dateDepart;
    private Integer nombrePersonnes;
    private String typeVoyage;
    private String commentaires;
}