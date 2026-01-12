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

-- Schema for the Game Moves/History table
CREATE TABLE IF NOT EXISTS game_moves
(
    id
    BIGINT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    game_id
    VARCHAR
(
    255
) NOT NULL,
    player_id
    BIGINT,
    move_type
    VARCHAR
(
    50
) NOT NULL,
    player_score_before
    INT,
    player_score_after
    INT,
    bank_score_before
    INT,
    bank_score_after
    INT,
    timestamp
    TIMESTAMP
    DEFAULT
    CURRENT_TIMESTAMP,
    FOREIGN
    KEY
    (player_id)
    REFERENCES
    players
    (id),
    FOREIGN
    KEY
    (game_id)
    REFERENCES
    games
    (id)
    );

-- Schema for the Games table
CREATE TABLE IF NOT EXISTS games
(
    id
    VARCHAR
(
    255
) PRIMARY
    KEY,
    player_id
    BIGINT,
    player_score
    INT
    DEFAULT
    0,
    bank_score
    INT
    DEFAULT
    0,
    game_ended
    BOOLEAN
    DEFAULT
    FALSE,
    current_player_turn
    VARCHAR
(
    20
) DEFAULT
    'PLAYER',
    result
    VARCHAR
(
    20
),
    created_at
    TIMESTAMP
    DEFAULT
    CURRENT_TIMESTAMP,
    updated_at
    TIMESTAMP
    DEFAULT
    CURRENT_TIMESTAMP
    ON
    UPDATE
    CURRENT_TIMESTAMP,
    FOREIGN
    KEY
    (player_id)
    REFERENCES
    players
    (id)
    );