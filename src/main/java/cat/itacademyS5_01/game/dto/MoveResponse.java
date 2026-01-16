package cat.itacademyS5_01.game.dto;

import cat.itacademyS5_01.player.dto.Name;

import java.util.UUID;

public record MoveResponse(UUID gameId,
                           Name playerName,
                           int playerScore,
                           int bankScore,
                           PlayerResult playerResult
) {
}
