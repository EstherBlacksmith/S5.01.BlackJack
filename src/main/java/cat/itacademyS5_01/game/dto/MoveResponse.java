package cat.itacademyS5_01.game.dto;

public record MoveResponse(GameId gameId,
                           String playerName,
                           int playerScore,
                           int bankScore,
                           PlayerResult playerResult
) {
}
