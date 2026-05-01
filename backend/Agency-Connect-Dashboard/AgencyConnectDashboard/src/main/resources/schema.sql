CREATE TABLE IF NOT EXISTS notification (
    id VARCHAR(36) PRIMARY KEY,
    event_name VARCHAR(50) NOT NULL,
    payload_json TEXT NOT NULL, -- JSON sérialisé
    status VARCHAR(20) NOT NULL,
    received_at TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS reservation (
    id VARCHAR(36) PRIMARY KEY,
    client_nom VARCHAR(100) NOT NULL,
    client_prenom VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    date_depart VARCHAR(50) NOT NULL,
    nombre_personnes INTEGER NOT NULL,
    type_voyage VARCHAR(50) NOT NULL,
    commentaires TEXT,
    created_at TIMESTAMP NOT NULL,
    source VARCHAR(20) NOT NULL
);
-- Modifier la colonne id pour générer un UUID par défaut
ALTER TABLE reservation
ALTER COLUMN id SET DEFAULT gen_random_uuid()::text;