-- Extension pour générer des UUID si nécessaire (bien que nous puissions les générer côté Java)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Table des événements reçus (Source de vérité & Idempotence)
CREATE TABLE IF NOT EXISTS webhook_event (
    id UUID PRIMARY KEY,
    idempotency_key VARCHAR(255) NOT NULL UNIQUE, -- Clé calculée (Hash SHA-256)
    event_name VARCHAR(100) NOT NULL,
    payload JSONB NOT NULL, -- Stockage du JSON brut pour audit/replay
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Index sur la clé d'idempotence pour la performance
CREATE INDEX IF NOT EXISTS idx_event_idempotency ON webhook_event(idempotency_key);

-- Table des notifications (État du traitement vers le système B)
CREATE TABLE IF NOT EXISTS notification (
    id UUID PRIMARY KEY,
    event_id UUID NOT NULL REFERENCES webhook_event(id),
    status VARCHAR(20) NOT NULL, -- PENDING, SENT, FAILED
    retry_count INT DEFAULT 0,
    last_error TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Index pour retrouver rapidement les notifications par statut (utile pour un batch de rattrapage éventuel)
CREATE INDEX IF NOT EXISTS idx_notification_status ON notification(status);