package cat.itacademyS5_01.game.dto;

import cat.itacademyS5_01.exception.InvalidMovementException;
import cat.itacademyS5_01.game.model.Wager;


public record MoveRequest(PlayerAction playerAction,
                          Wager wager) {

    public MoveRequest {
        if (playerAction == null) {
            throw new InvalidMovementException(
                    "Invalid action"
            );
        }

        PlayerAction.valueOf(playerAction.toString().toUpperCase());

    }
}
