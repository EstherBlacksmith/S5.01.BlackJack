package cat.itacademyS5_01.game.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record MoveRequest(@NotBlank(message = "Game ID must not be blank")
                           String gameId,
                           PlayerAction playerAction,
                           @Min(value=10,message = "The minimum wager is 10€")
                           int wager) {
}
