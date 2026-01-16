package cat.itacademyS5_01.betting.service;

import cat.itacademyS5_01.exception.InvalidMovementException;
import cat.itacademyS5_01.game.dto.*;
import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.game.service.GameService;
import cat.itacademyS5_01.game.strategy.PlayerActionStrategy;
import cat.itacademyS5_01.player.dto.Name;
import cat.itacademyS5_01.player.service.PlayerService;
import cat.itacademyS5_01.player.service.PlayerStatsService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

@Service
public class BettingService {
    private final PlayerService playerService;
    private final GameService gameService;
    private final PlayerStatsService playerStatsService;
    private final Map<PlayerAction, PlayerActionStrategy> strategies;

    public BettingService(PlayerService playerService, GameService gameService, Map<PlayerAction, PlayerActionStrategy> strategies, PlayerStatsService playerStatsService) {
        this.playerService = playerService;
        this.gameService = gameService;
        this.playerStatsService = playerStatsService;
        this.strategies = strategies;

    }

    public Mono<GameResponse> startGame(GameRequest gameRequest) {
        Name playerName = gameRequest.playerName();

        return playerService.findByName(playerName)
                .switchIfEmpty(playerService.create(playerName))
                .flatMap(player -> {
                    Game game = new Game(playerName);
                    return gameService.save(game);
                })
                .map(game -> new GameResponse(
                        game.getId(),
                        game.getPlayerName(),
                        game.getCurrentPlayerScore(),
                        game.getCurrentBankScore()
                ));
    }

    public Mono<Game> makeMove(UUID gameId, MoveRequest moveRequest) {
        return gameService.findById(gameId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Game not found with ID: " + gameId)))
                .flatMap(game -> {
                    PlayerActionStrategy playerActionStrategy = strategies.get(moveRequest.playerAction());
                    if (playerActionStrategy == null) {
                        return Mono.error(new InvalidMovementException("Invalid player action: " + moveRequest.playerAction()));
                    }
                    return playerActionStrategy.execute(game, moveRequest.wager());
                })
                .flatMap(game -> finishGame(game));
    }

    private Mono<? extends Game> finishGame(Game game) {
        PlayerResult result = game.determineWinner();
        game.setResult(result);
        game.setGameOver(true);

        return playerStatsService
                .updatePlayerStats(game.getPlayerName(), result)
                .then(gameService.save(game));
    }

}
