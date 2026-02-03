package com.example.AgencyConnectDashboard.dashboard.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EventRequestDTO {
    private String nomClient;
    private String prenomClient;
    private String emailClient;
    private String telephoneClient;
    private String paysOrigineClient;
    private String destinationSouhaiteeClient;
    private String dateDepartClient;
    private Integer nombrePersonnes;
    private String typeVoyage;
    private String commentaires;
    private String nomEvent;
}