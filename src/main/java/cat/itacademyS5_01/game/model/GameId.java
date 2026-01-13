package cat.itacademyS5_01.game.model;


import cat.itacademyS5_01.exception.MissingIdentifierException;

public record GameId(String value) {
    public GameId {
        if (value == null || value.isBlank()) {
            try {
                throw new MissingIdentifierException("Game ID cannot be null or blank");
            } catch (MissingIdentifierException e) {
                throw new RuntimeException(e);
            }
        }
    }
}