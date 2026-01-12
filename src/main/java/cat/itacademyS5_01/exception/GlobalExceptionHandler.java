package cat.itacademyS5_01.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>("Global Error: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingNameException.class)
    public ResponseEntity<String> handleMissingNameException(MissingNameException ex) {
        return new ResponseEntity<>("Missing name: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PlayerAlreadyExistsException.class)
    public ResponseEntity<String> handlePlayerAlreadyExistsYException(PlayerAlreadyExistsException ex) {
        return new ResponseEntity<>("Player already exists with that name: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingIdentifierException.class)
    public ResponseEntity<String> handleMissingIdentifierException(MissingIdentifierException ex) {
        return new ResponseEntity<>("Missing identifier " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
