package cat.itacademyS5_01.game.dto;

import cat.itacademyS5_01.exception.InvalidMovementException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record MoveRequest(@NotBlank(message = "Game ID must not be blank")
                          String gameId,

                          PlayerAction playerAction,
                          @Min(value = 10, message = "The minimum wager is 10€")
                          int wager) {

    public MoveRequest {
        if (playerAction == null) {
            throw new InvalidMovementException(
                    "Invalid action"
            );
        }
        try {
            PlayerAction.valueOf(playerAction.toString().toUpperCase());
        } catch (InvalidMovementException e) {
            throw new InvalidMovementException(
                    "Invalid action. Allowed movements: " + String.join(", ",
                            java.util.Arrays.stream(PlayerAction.values())
                                    .map(Enum::name)
                                    .toList())
            );
        }
    }
}
