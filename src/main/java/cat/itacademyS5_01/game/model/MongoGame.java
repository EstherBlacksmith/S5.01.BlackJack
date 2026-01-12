package cat.itacademyS5_01.game.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * MongoDB Document model for Game
 * Stores game state in MongoDB for comparison with MySQL implementation
 */
@Document(collection = "games")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@ToString
public class MongoGame {
    
    @Id
    private String id;
    
    private Long playerId;
    private String playerName;
    private int playerScore;
    private int bankScore;
    private boolean gameEnded;
    private String currentPlayerTurn; // "PLAYER" or "BANK"
    private String result; // "WIN", "LOSE", "PUSH", or null
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @Builder.Default
    private List<GameMove> moves = new ArrayList<>();
    
    /**
     * Nested class for game moves history
     */
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    @ToString
    public static class GameMove {
        private String moveType; // "HIT", "STAND", "DOUBLE_DOWN"
        private int playerScoreBefore;
        private int playerScoreAfter;
        private int bankScoreBefore;
        private int bankScoreAfter;
        private LocalDateTime timestamp;
    }
    
    /**
     * Add a move to the game history
     */
    public void addMove(GameMove move) {
        this.moves.add(move);
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Convert from JPA Game entity to MongoDB document
     */
    public static MongoGame fromJpaGame(Game jpaGame) {
        return MongoGame.builder()
                .id(jpaGame.getId())
                .playerId(jpaGame.getPlayerId())
                .playerName(jpaGame.getPlayerName())
                .playerScore(jpaGame.getPlayerScore())
                .bankScore(jpaGame.getBankScore())
                .gameEnded(jpaGame.isGameEnded())
                .currentPlayerTurn(jpaGame.getCurrentPlayerTurn())
                .result(jpaGame.getResult())
                .createdAt(jpaGame.getCreatedAt() != null ? jpaGame.getCreatedAt().toLocalDateTime() : LocalDateTime.now())
                .updatedAt(jpaGame.getUpdatedAt() != null ? jpaGame.getUpdatedAt().toLocalDateTime() : LocalDateTime.now())
                .build();
    }
    
    /**
     * Convert from MongoDB document to JPA Game entity
     */
    public Game toJpaGame() {
        Game game = new Game();
        game.setId(this.id);
        game.setPlayerId(this.playerId);
        game.setPlayerName(this.playerName);
        game.setPlayerScore(this.playerScore);
        game.setBankScore(this.bankScore);
        game.setGameEnded(this.gameEnded);
        game.setCurrentPlayerTurn(this.currentPlayerTurn);
        game.setResult(this.result);
        // Note: Timestamps would need proper conversion for JPA
        return game;
    }
}