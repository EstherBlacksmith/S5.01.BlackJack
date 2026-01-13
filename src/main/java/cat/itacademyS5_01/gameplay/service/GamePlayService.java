package cat.itacademyS5_01.gameplay.service;

import cat.itacademyS5_01.exception.InvalidMovementException;
import cat.itacademyS5_01.game.dto.GameRequest;
import cat.itacademyS5_01.game.dto.GameResponse;
import cat.itacademyS5_01.game.dto.MoveRequest;
import cat.itacademyS5_01.game.dto.PlayerResult;
import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.game.model.GameId;
import cat.itacademyS5_01.game.model.Wager;
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
                        default:
                            return Mono.error(new InvalidMovementException("Invalid player action: " + moveRequest.playerAction()));
                    }
                });
    }

    private Mono<? extends Game> handleDoubleDownAction(Game game, Wager wager) {
        game.setPlayerScore(game.getPlayerScore() + drawCard());

        if (game.getPlayerScore() > 21) {
            game.setPlayerScore(0);
        }
        return gameService.save(game);
    }

    private Mono<? extends Game> handleStandAction(Game game) {
        while (game.getBankScore() < 17) {
            int newCard = drawCard();
            game.setBankScore(game.getBankScore() + newCard);
        }

        determineWinner(game);

        return gameService.save(game);
    }


    private Mono<? extends Game> handleHitAction(Game game,  Wager wager) {

        int newCard = drawCard();
        game.setPlayerScore(game.getPlayerScore() + newCard);

        if (game.getPlayerScore() > 21) {
            game.setPlayerScore(0);
        }

        return gameService.save(game);
    }

    private int drawCard() {
        return (int) (Math.random() * 11) + 1;
    }

    private void determineWinner(Game game) {
        int playerScore = game.getPlayerScore();
        int bankScore = game.getBankScore();

        if (playerScore > 21) {
            game.setResult(PlayerResult.LOSE);
            game.setPlayerScore(0);
        } else if (bankScore > 21) {
            game.setResult(PlayerResult.WIN);
        } else if (playerScore > bankScore) {
            game.setPlayerScore(1);
            game.setResult(PlayerResult.WIN);
        } else if (bankScore > playerScore) {
            game.setPlayerScore(0);
            game.setResult(PlayerResult.LOSE);
        } else {
            // Empate
             game.setResult(PlayerResult.TIE);
        }
//TODO:implement logic to increment the wins in the scores
    }

}
