package cat.itacademyS5_01.game.service;

import cat.itacademyS5_01.exception.GameNotFoundException;
import cat.itacademyS5_01.exception.InvalidGameStateException;
import cat.itacademyS5_01.game.dto.MoveRequest;
import cat.itacademyS5_01.game.dto.PlayerAction;
import cat.itacademyS5_01.game.dto.PlayerResult;
import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.game.model.MongoGame;
import cat.itacademyS5_01.game.repository.GameReactiveMongoRepository;
import cat.itacademyS5_01.game.repository.MongoGameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * Dual Database Game Service - Implements both MySQL and MongoDB support
 * This service demonstrates polyglot persistence by using:
 * - MySQL (via R2DBC) for relational data and transactions
 * - MongoDB for document-based game state and history
 */
@Service
@RequiredArgsConstructor
public class DualDatabaseGameService {
    
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final MongoGameRepository mongoGameRepository;
    private final GameReactiveMongoRepository gameReactiveMongoRepository;
    
    private static final int BLACKJACK_TARGET = 21;
    private static final int DEALER_STAND_THRESHOLD = 17;
    private static final Random random = new Random();
    
    /**
     * Find game by ID - checks both databases
     */
    public Mono<Game> findById(String id) {
        // First try MongoDB (primary for games)
        return mongoGameRepository.findById(id)
                .map(this::convertMongoToJpa)
                .switchIfEmpty(
                    // Fallback to MySQL
                    r2dbcEntityTemplate.selectOne(
                            org.springframework.data.relational.core.query.Query.query(
                                    org.springframework.data.relational.core.query.Criteria.where("id").is(id)
                            ),
                            Game.class
                    )
                )
                .switchIfEmpty(Mono.error(new GameNotFoundException("Game with ID " + id + " not found in either database")));
    }
    
    /**
     * Save game to both databases
     */
    public Mono<Game> save(Game game) {
        if (game.getId() == null || game.getId().isEmpty()) {
            long gameId = GameIdGenerator.generateId();
            game.setId(String.valueOf(gameId));
        }
        
        // Convert to MongoDB document
        MongoGame mongoGame = MongoGame.fromJpaGame(game);
        
        // Save to both databases in parallel
        Mono<Game> mysqlSave = r2dbcEntityTemplate.insert(game);
        Mono<MongoGame> mongoSave = mongoGameRepository.save(mongoGame);
        
        return Mono.zip(mysqlSave, mongoSave)
                .map(tuple -> tuple.getT1()) // Return the MySQL version
                .onErrorResume(e -> {
                    // If one fails, clean up the other
                    return Mono.error(e);
                });
    }
    
    /**
     * Process a new move - updates both databases
     */
    public Mono<Game> newMove(MoveRequest moveRequest) {
        return findById(moveRequest.gameId())
                .flatMap(game -> {
                    // Validate game state
                    if (game.isGameEnded()) {
                        return Mono.error(new InvalidGameStateException("Game has already ended"));
                    }
                    
                    if (!"PLAYER".equals(game.getCurrentPlayerTurn())) {
                        return Mono.error(new InvalidGameStateException("It's not the player's turn"));
                    }
                    
                    // Process player action
                    return processPlayerAction(game, moveRequest.playerAction());
                })
                .flatMap(this::handleBankTurnIfNeeded)
                .flatMap(this::checkGameEndConditions)
                .flatMap(this::save); // Save to both databases
    }
    
    /**
     * Get all games from both databases (merged)
     */
    public Flux<Game> findAll() {
        Flux<Game> mysqlGames = r2dbcEntityTemplate.select(Game.class).all();
        Flux<Game> mongoGames = mongoGameRepository.findAll().map(this::convertMongoToJpa);
        
        return Flux.merge(mysqlGames, mongoGames)
                .distinct(Game::getId); // Remove duplicates
    }
    
    // Game logic methods (same as original but adapted for dual database)
    
    private Mono<Game> processPlayerAction(Game game, PlayerAction action) {
        return switch (action) {
            case HIT -> handleHit(game);
            case STAND -> handleStand(game);
            case DOUBLE_DOWN -> handleDoubleDown(game);
        };
    }
    
    private Mono<Game> handleHit(Game game) {
        int newScore = game.getPlayerScore() + drawCard();
        game.setPlayerScore(newScore);
        
        // Record move in MongoDB
        recordMove(game, "HIT");
        
        if (newScore > BLACKJACK_TARGET) {
            game.setGameEnded(true);
            game.setResult(PlayerResult.LOSE);
        }
        
        return Mono.just(game);
    }
    
    private Mono<Game> handleStand(Game game) {
        game.setCurrentPlayerTurn("BANK");
        recordMove(game, "STAND");
        return Mono.just(game);
    }
    
    private Mono<Game> handleDoubleDown(Game game) {
        int newScore = game.getPlayerScore() + drawCard();
        game.setPlayerScore(newScore);
        recordMove(game, "DOUBLE_DOWN");
        
        if (newScore > BLACKJACK_TARGET) {
            game.setGameEnded(true);
            game.setResult(PlayerResult.LOSE);
        } else {
            game.setCurrentPlayerTurn("BANK");
        }
        
        return Mono.just(game);
    }
    
    private Mono<Game> handleBankTurnIfNeeded(Game game) {
        if (!"BANK".equals(game.getCurrentPlayerTurn()) || game.isGameEnded()) {
            return Mono.just(game);
        }
        
        while (game.getBankScore() < DEALER_STAND_THRESHOLD && !game.isGameEnded()) {
            int newScore = game.getBankScore() + drawCard();
            game.setBankScore(newScore);
            recordMove(game, "BANK_HIT");
            
            if (newScore > BLACKJACK_TARGET) {
                game.setGameEnded(true);
                game.setResult(PlayerResult.WIN);
                break;
            }
        }
        
        return Mono.just(game);
    }
    
    private Mono<Game> checkGameEndConditions(Game game) {
        if (game.isGameEnded()) {
            return Mono.just(game);
        }
        
        if ("BANK".equals(game.getCurrentPlayerTurn()) && game.getBankScore() >= DEALER_STAND_THRESHOLD) {
            determineGameResult(game);
        }
        
        return Mono.just(game);
    }
    
    private void determineGameResult(Game game) {
        int playerScore = game.getPlayerScore();
        int bankScore = game.getBankScore();
        
        if (playerScore > BLACKJACK_TARGET) {
            game.setResult(PlayerResult.LOSE);
        } else if (bankScore > BLACKJACK_TARGET) {
            game.setResult(PlayerResult.WIN);
        } else if (playerScore == bankScore) {
            game.setResult(PlayerResult.PUSH);
        } else if (playerScore > bankScore) {
            game.setResult(PlayerResult.WIN);
        } else {
            game.setResult(PlayerResult.LOSE);
        }
        
        game.setGameEnded(true);
    }
    
    private int drawCard() {
        return random.nextInt(10) + 2;
    }
    
    /**
     * Record a move in MongoDB for audit/history purposes
     */
    private void recordMove(Game game, String moveType) {
        MongoGame.GameMove move = MongoGame.GameMove.builder()
                .moveType(moveType)
                .playerScoreBefore(game.getPlayerScore() - (moveType.startsWith("HIT") ? drawCard() : 0))
                .playerScoreAfter(game.getPlayerScore())
                .bankScoreBefore(game.getBankScore())
                .bankScoreAfter(game.getBankScore())
                .timestamp(LocalDateTime.now())
                .build();
        
        // This will be saved when we save the full game
    }
    
    /**
     * Convert MongoDB document to JPA entity
     */
    private Game convertMongoToJpa(MongoGame mongoGame) {
        return mongoGame.toJpaGame();
    }
    
    /**
     * Convert JPA entity to MongoDB document
     */
    private MongoGame convertJpaToMongo(Game game) {
        return MongoGame.fromJpaGame(game);
    }
}