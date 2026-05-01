-- liquibase formatted sql

-- changeset ton_nom:1
-- ========================================
-- TABLE : subscriptions
-- ========================================
CREATE TABLE IF NOT EXISTS subscriptions (
    id          UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    app_name    VARCHAR(100) NOT NULL,
    target_url  VARCHAR(500) NOT NULL,
    event_type  VARCHAR(100) NOT NULL,
    is_active   BOOLEAN     NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP   NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP   NOT NULL DEFAULT NOW()
);

-- ========================================
-- TABLE : webhook_events
-- ========================================
CREATE TABLE IF NOT EXISTS webhook_events (
    id          UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    event_type  VARCHAR(100) NOT NULL,
    source_app  VARCHAR(100) NOT NULL,
    payload     TEXT        NOT NULL,
    status      VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    created_at  TIMESTAMP   NOT NULL DEFAULT NOW()
);

-- ========================================
-- TABLE : delivery_attempts
-- ========================================
CREATE TABLE IF NOT EXISTS delivery_attempts (
    id              UUID        PRIMARY KEY DEFAULT gen_random_uuid(),
    event_id        UUID        NOT NULL REFERENCES webhook_events(id),
    subscription_id UUID        NOT NULL REFERENCES subscriptions(id),
    attempt_number  INT         NOT NULL DEFAULT 1,
    status          VARCHAR(20) NOT NULL,
    http_status     INT,
    response_body   TEXT,
    error_message   TEXT,
    attempted_at    TIMESTAMP   NOT NULL DEFAULT NOW()
);

-- changeset ton_nom:2
CREATE INDEX IF NOT EXISTS idx_subscriptions_event_type ON subscriptions(event_type);
CREATE INDEX IF NOT EXISTS idx_subscriptions_active ON subscriptions(is_active);
CREATE INDEX IF NOT EXISTS idx_webhook_events_status ON webhook_events(status);
CREATE INDEX IF NOT EXISTS idx_delivery_attempts_event_id ON delivery_attempts(event_id);