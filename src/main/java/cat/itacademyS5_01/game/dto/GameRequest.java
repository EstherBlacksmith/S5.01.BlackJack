package cat.itacademyS5_01.game.dto;

import jakarta.validation.constraints.NotBlank;

public record GameRequest(@NotBlank(message = "Player name must not be blank")
                          String playerName) {

}
