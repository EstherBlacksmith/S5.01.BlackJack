package cat.itacademyS5_01.game.dto;

public record GameResponse (
        int gameId,
        String playerName,
        int playerScore,
        int bankScore
) {}
