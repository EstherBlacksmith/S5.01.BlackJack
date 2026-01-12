package cat.itacademyS5_01.gameplay.service;

import cat.itacademyS5_01.game.dto.GameRequest;
import cat.itacademyS5_01.game.dto.GameResponse;
import cat.itacademyS5_01.game.dto.MoveRequest;
import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.game.service.DualDatabaseGameService;
import cat.itacademyS5_01.player.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Dual Database Game Play Service - Uses both MySQL and MongoDB
 * This service demonstrates polyglot persistence by using:
 * - MySQL (via R2DBC) for relational player data
 * - MongoDB for document-based game state and history
 */
@Service
@RequiredArgsConstructor
public class DualDatabaseGamePlayService {
    
    private static final Logger logger = LoggerFactory.getLogger(DualDatabaseGamePlayService.class);
    
    private final PlayerService playerService;
    private final DualDatabaseGameService dualDatabaseGameService;
    
    /**
     * Starts a new game for the specified player using dual database persistence.
     * 
     * @param gameRequest the request containing player name
     * @return Mono containing the created game response
     */
    public Mono<GameResponse> startGame(GameRequest gameRequest) {
        String playerName = gameRequest.playerName();
        logger.info("Starting new game with dual database support for player: {}", playerName);
        
        return playerService.findByName(playerName)
                .switchIfEmpty(playerService.create(playerName))
                .flatMap(player -> {
                    Game game = new Game(playerName);
                    game.setPlayerId(player.getId()); // Set player ID for relational integrity
                    logger.debug("Created new game object for player: {}", playerName);
                    return dualDatabaseGameService.save(game);
                })
                .map(game -> {
                    logger.info("Game started successfully with dual database persistence. Game ID: {}", game.getId());
                    return new GameResponse(
                            game.getId(),
                            game.getPlayerName(),
                            game.getPlayerScore(),
                            game.getBankScore()
                    );
                })
                .onErrorResume(e -> {
                    logger.error("Failed to start game with dual database for player {}: {}", playerName, e.getMessage());
                    return Mono.error(e);
                });
    }
    
    /**
     * Processes a player's move in an existing game using dual database persistence.
     * 
     * @param moveRequest the move request containing game ID, player action, and wager
     * @return Mono containing the updated game state
     */
    public Mono<Game> makeMove(MoveRequest moveRequest) {
        String gameId = moveRequest.gameId();
        String playerAction = moveRequest.playerAction().toString();
        
        logger.info("Processing move with dual database support for game {}: player action = {}", gameId, playerAction);
        
        return dualDatabaseGameService.newMove(moveRequest)
                .doOnSuccess(game -> {
                    logger.info("Move processed successfully with dual database persistence for game {}. Player score: {}, Bank score: {}", 
                            gameId, game.getPlayerScore(), game.getBankScore());
                    
                    if (game.isGameEnded()) {
                        logger.info("Game {} has ended with dual database persistence. Result: {}", gameId, game.getResult());
                    }
                })
                .doOnError(e -> {
                    logger.error("Failed to process move with dual database for game {}: {}", gameId, e.getMessage());
                });
    }
    
    /**
     * Gets game by ID from either database
     * 
     * @param gameId the game ID
     * @return Mono containing the game
     */
    public Mono<Game> getGame(String gameId) {
        logger.info("Retrieving game with dual database support for game ID: {}", gameId);
        
        return dualDatabaseGameService.findById(gameId)
                .doOnSuccess(game -> {
                    logger.debug("Successfully retrieved game {} from database. Player: {}, Status: {}", 
                            gameId, game.getPlayerName(), game.isGameEnded() ? "ENDED" : "ACTIVE");
                })
                .doOnError(e -> {
                    logger.error("Failed to retrieve game with dual database for game ID {}: {}", gameId, e.getMessage());
                });
    }
}