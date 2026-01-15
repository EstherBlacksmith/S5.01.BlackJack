package cat.itacademyS5_01.game.dto;

public record GameResponse(
        java.util.UUID gameId,
        String playerName,
        int currentPlayerScore,
        int currentBankScore
) {
}
