package cat.itacademyS5_01.exception;

public class PlayerAlreadyExists extends RuntimeException {
    public PlayerAlreadyExists(String message) {
        super(message);
    }
}
