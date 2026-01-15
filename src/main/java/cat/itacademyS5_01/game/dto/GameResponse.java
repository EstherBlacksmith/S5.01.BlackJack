package cat.itacademyS5_01.game.dto;

public record GameResponse(
        java.util.UUID gameId,
        cat.itacademyS5_01.player.dto.Name playerName,
        int currentPlayerScore,
        int currentBankScore
) {
}
