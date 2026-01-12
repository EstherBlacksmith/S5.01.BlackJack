-- Schema for the Player table
CREATE TABLE IF NOT EXISTS players
(
    id
    BIGINT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    name
    VARCHAR
(
    255
) NOT NULL
    );