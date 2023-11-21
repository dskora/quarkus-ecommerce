DROP TABLE IF EXISTS events;

CREATE TABLE events (
    id VARCHAR(255) PRIMARY KEY,
    aggregate_type LONGTEXT NOT NULL,
    aggregate_id VARCHAR(255),
    type VARCHAR(255),
    payload LONGTEXT NOT NULL,
    processed SMALLINT DEFAULT 0
);

