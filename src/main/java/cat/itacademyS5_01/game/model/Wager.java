package cat.itacademyS5_01.game.model;

import cat.itacademyS5_01.exception.InvalidWagerException;
public record Wager(int value) {

    public Wager {
        if (value<10) {
            try {
                throw new InvalidWagerException("The minimum wager is 10€");
            } catch (InvalidWagerException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
