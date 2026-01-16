package cat.itacademyS5_01.game.dto;

import java.util.UUID;

public record MoveResponse(UUID gameId,
                           String playerName,
                           int playerScore,
                           int bankScore,
                           PlayerResult playerResult
) {
}
