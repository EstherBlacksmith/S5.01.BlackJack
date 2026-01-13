package cat.itacademyS5_01.game.dto;

import cat.itacademyS5_01.game.model.GameId;

public record MoveResponse(GameId gameId,
                           String playerName,
                           int playerScore,
                           int bankScore,
                           PlayerResult playerResult
) {
}
