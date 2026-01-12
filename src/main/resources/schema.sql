-- Schema for the Player table
CREATE TABLE IF NOT EXISTS players (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);