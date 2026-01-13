package cat.itacademyS5_01.gameplay.service;

import cat.itacademyS5_01.exception.InvalidMovementException;
import cat.itacademyS5_01.game.dto.GameRequest;
import cat.itacademyS5_01.game.dto.GameResponse;
import cat.itacademyS5_01.game.dto.MoveRequest;
import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.game.model.GameId;
import cat.itacademyS5_01.game.service.GameService;
import cat.itacademyS5_01.player.service.PlayerService;
import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GamePlayService {
    private final PlayerService playerService;
    private final GameService gameService;

    public GamePlayService(PlayerService playerService, GameService gameService) {
        this.playerService = playerService;
        this.gameService = gameService;
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
                        game.getPlayerScore(),
                        game.getBankScore()
                ));
    }

    public Mono<Game> makeMove(GameId gameId, MoveRequest moveRequest) {
        return gameService.findById(gameId)
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Game not found with ID: " + gameId.value())))
                .flatMap(game -> {
                    switch (moveRequest.playerAction()) {
                        case HIT:
                            return handleHitAction(game, moveRequest.wager());
                        case STAND:
                            return handleStandAction(game);
                        case DOUBLE_DOWN:
                            return handleDoubleDownAction(game, moveRequest.wager());
                        case SPLIT:
                            return handleSplitAction(game, moveRequest.wager());
                        default:
                            return Mono.error(new InvalidMovementException("Invalid player action: " + moveRequest.playerAction()));
                    }
                });
    }

    private Mono<? extends Game> handleSplitAction(Game game, @Min(value = 10, message = "The minimum wager is 10€") int wager) {
    }

    private Mono<? extends Game> handleDoubleDownAction(Game game, @Min(value = 10, message = "The minimum wager is 10€") int wager) {
    }

    private Mono<? extends Game> handleStandAction(Game game) {
    }

    private Mono<? extends Game> handleHitAction(Game game, @Min(value = 10, message = "The minimum wager is 10€") int wager) {
    }

}
