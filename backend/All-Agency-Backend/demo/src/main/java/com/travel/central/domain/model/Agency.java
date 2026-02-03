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
@Table("agency")
public class Agency {
    @Id
    private Long id;
    private String nom;
    private String localisation;
    private String description;
    private String image;
    private String callbackUrl;
}