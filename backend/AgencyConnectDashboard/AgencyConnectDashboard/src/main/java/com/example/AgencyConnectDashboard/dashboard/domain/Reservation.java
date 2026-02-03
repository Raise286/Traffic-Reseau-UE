package com.example.AgencyConnectDashboard.dashboard.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("reservation")
public class Reservation {

    @Id
    private String id;

    @Column("client_nom")
    private String clientNom;

    @Column("client_prenom")
    private String clientPrenom;

    private String destination;

    @Column("date_depart")
    private String dateDepart;

    @Column("nombre_personnes")
    private Integer nombrePersonnes;

    @Column("type_voyage")
    private String typeVoyage;

    private String commentaires;

    @Column("created_at")
    private LocalDateTime createdAt;

    private String source;
}
