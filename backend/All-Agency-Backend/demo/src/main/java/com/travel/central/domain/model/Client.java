package com.travel.central.domain.model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("client")
public class Client {
    @Id
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String paysOrigine;
    private String destinationSouhaitee;
}