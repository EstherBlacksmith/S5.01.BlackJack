package cat.itacademyS5_01.gameplay.service;

import cat.itacademyS5_01.game.dto.GameRequest;
import cat.itacademyS5_01.game.dto.GameResponse;
import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.game.service.GameService;
import cat.itacademyS5_01.player.service.PlayerService;
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
                    Game game = new Game(player.getName());
                    return gameService.save(game);
                })
                .map(game -> new GameResponse(
                        game.getId(),
                        game.getPlayerName(),
                        game.getPlayerScore(),
                        game.getBankScore()
                ));
    }
}
