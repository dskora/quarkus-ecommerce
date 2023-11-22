DROP TABLE IF EXISTS events;

CREATE TABLE events (
    id VARCHAR(255) PRIMARY KEY,
    aggregatetype VARCHAR(255),
    aggregateid VARCHAR(255),
    type VARCHAR(255),
    payload LONGTEXT
);