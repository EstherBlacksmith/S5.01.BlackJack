package cat.itacademyS5_01.game.model;

public record Wager(int value) {

    public Wager {
        if (value<10) {
            throw new IllegalArgumentException("The minimum wager is 10€");
        }
    }
}
