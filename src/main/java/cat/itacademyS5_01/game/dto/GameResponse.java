package cat.itacademyS5_01.game.dto;

public record GameResponse(
        String gameId,
        String playerName,
        int playerScore,
        int bankScore
) {
}
