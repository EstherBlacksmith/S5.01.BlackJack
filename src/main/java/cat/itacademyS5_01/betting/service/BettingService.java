package cat.itacademyS5_01.betting.service;

import cat.itacademyS5_01.exception.InvalidMovementException;
import cat.itacademyS5_01.game.dto.*;
import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.game.model.GameId;
import cat.itacademyS5_01.game.service.GameService;
import cat.itacademyS5_01.game.strategy.PlayerActionStrategy;
import cat.itacademyS5_01.player.service.PlayerService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class BettingService {
    private final PlayerService playerService;
    private final GameService gameService;
    private final Map<PlayerAction, PlayerActionStrategy> strategies;

    public BettingService(PlayerService playerService, GameService gameService, Map<PlayerAction, PlayerActionStrategy> strategies) {
        this.playerService = playerService;
        this.gameService = gameService;
        this.strategies = strategies;
    }

    public Mono<GameResponse> startGame(GameRequest gameRequest) {
        String playerName = gameRequest.playerName();

        return playerService.findByName(playerName)
                .switchIfEmpty(playerService.create(playerName))
                .flatMap(player -> {
                    Game game = new Game(playerName);
                    return gameService.save(game);
                })
                .map(game -> new GameResponse(
                        game.getId(),
                        game.getPlayerName(),
                        game.getGamesWon(),
                        game.getGamesLost()
                ));
    }

    public Mono<Game> makeMove(GameId gameId, MoveRequest moveRequest) {
        return gameService.findById(gameId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Game not found with ID: " + gameId.value())))
                .flatMap(game -> {
                    PlayerActionStrategy playerActionStrategy = strategies.get(moveRequest.playerAction());
                    if (playerActionStrategy == null) {
                        return Mono.error(new InvalidMovementException("Invalid player action: " + moveRequest.playerAction()));
                    }
                    return playerActionStrategy.execute(game, moveRequest.wager());
                });
    }
}
