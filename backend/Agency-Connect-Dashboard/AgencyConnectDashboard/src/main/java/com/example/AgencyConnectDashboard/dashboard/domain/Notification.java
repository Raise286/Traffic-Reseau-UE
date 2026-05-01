package com.example.AgencyConnectDashboard.dashboard.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("notification")
public class Notification implements Persistable<String> {

    @Id
    private String id;

    @Column("event_name")
    private String eventName;

    @Column("payload_json")
    private String payloadJson;

    private NotificationStatus status;

    @Column("received_at")
    private LocalDateTime receivedAt;

    // ✅ AJOUT : Ce flag indique à R2DBC que c'est une nouvelle entité
    @Transient
    @Builder.Default
    private boolean isNew = true;

    // ✅ Méthode de Persistable pour retourner l'ID
    @Override
    public String getId() {
        return this.id;
    }

    // ✅ Méthode de Persistable qui dit si l'entité est nouvelle
    @Override
    public boolean isNew() {
        return this.isNew || this.id == null;
    }

    // ✅ Méthode à appeler après l'insertion pour marquer comme "non nouvelle"
    public Notification markAsNotNew() {
        this.isNew = false;
        return this;
    }
}