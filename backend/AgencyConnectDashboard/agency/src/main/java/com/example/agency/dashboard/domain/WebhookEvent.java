package com.example.agency.dashboard.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("webhook_event")
public class WebhookEvent implements Persistable<UUID> {

    @Id
    private UUID id;

    @Column("idempotency_key")
    private String idempotencyKey;

    @Column("event_name")
    private String eventName;

    // CHANGEMENT ICI : On utilise String au lieu de Json
    private String payload;

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;

    @Transient
    @Builder.Default
    private boolean newEvent = false;

    @Override
    public boolean isNew() {
        return this.newEvent || id == null;
    }
}