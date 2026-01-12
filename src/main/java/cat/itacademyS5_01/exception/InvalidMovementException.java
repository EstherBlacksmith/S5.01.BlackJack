package cat.itacademyS5_01.exception;

public class InvalidMovementException extends RuntimeException {
    public InvalidMovementException(String message) {
        super(message);
    }
}
