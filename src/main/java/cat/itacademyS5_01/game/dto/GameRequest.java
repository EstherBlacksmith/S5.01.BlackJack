package cat.itacademyS5_01.game.dto;

import cat.itacademyS5_01.player.dto.Name;
import jakarta.validation.constraints.NotBlank;

public record GameRequest(Name playerName) {

}
