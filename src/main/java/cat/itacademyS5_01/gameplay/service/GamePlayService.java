package cat.itacademyS5_01.gameplay.service;

import cat.itacademyS5_01.game.dto.GameRequest;
import cat.itacademyS5_01.game.dto.GameResponse;
import cat.itacademyS5_01.game.dto.MoveRequest;
import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.game.service.GameService;
import cat.itacademyS5_01.player.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GamePlayService {
    
    private static final Logger logger = LoggerFactory.getLogger(GamePlayService.class);
    
    private final PlayerService playerService;
    private final GameService gameService;

    public GamePlayService(PlayerService playerService, GameService gameService) {
        this.playerService = playerService;
        this.gameService = gameService;
    }

    /**
     * Starts a new game for the specified player.
     * 
     * @param gameRequest the request containing player name
     * @return Mono containing the created game response
     */
    public Mono<GameResponse> startGame(GameRequest gameRequest) {
        String playerName = gameRequest.playerName();
        logger.info("Starting new game for player: {}", playerName);

        return playerService.findByName(playerName)
                .switchIfEmpty(playerService.create(playerName))
                .flatMap(player -> {
                    Game game = new Game(playerName);
                    logger.debug("Created new game object for player: {}", playerName);
                    return gameService.save(game);
                })
                .map(game -> {
                    logger.info("Game started successfully. Game ID: {}", game.getId());
                    return new GameResponse(
                            game.getId(),
                            game.getPlayerName(),
                            game.getPlayerScore(),
                            game.getBankScore()
                    );
                })
                .onErrorResume(e -> {
                    logger.error("Failed to start game for player {}: {}", playerName, e.getMessage());
                    return Mono.error(e);
                });
    }

    /**
     * Processes a player's move in an existing game.
     * 
     * @param moveRequest the move request containing game ID, player action, and wager
     * @return Mono containing the updated game state
     * @throws InvalidGameStateException if the game is in an invalid state for the requested move
     * @throws GameNotFoundException if the game with the specified ID is not found
     */
    public Mono<Game> makeMove(MoveRequest moveRequest) {
        String gameId = moveRequest.gameId();
        String playerAction = moveRequest.playerAction().toString();
        
        logger.info("Processing move for game {}: player action = {}", gameId, playerAction);

        return gameService.newMove(moveRequest)
                .doOnSuccess(game -> {
                    logger.info("Move processed successfully for game {}. Player score: {}, Bank score: {}", 
                            gameId, game.getPlayerScore(), game.getBankScore());
                    
                    if (game.isGameEnded()) {
                        logger.info("Game {} has ended. Result: {}", gameId, game.getResult());
                    }
                })
                .doOnError(e -> {
                    logger.error("Failed to process move for game {}: {}", gameId, e.getMessage());
                });
    }
}
