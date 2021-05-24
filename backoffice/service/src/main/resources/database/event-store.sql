CREATE TABLE IF NOT EXISTS domain_events (
    id INT(10) NOT NULL AUTO_INCREMENT,
    event_id CHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    version INT(10) NOT NULL,
    body JSON NOT NULL,
    occurred_on TIMESTAMP NOT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
