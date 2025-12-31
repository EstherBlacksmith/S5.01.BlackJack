package cat.itacademyS5_01.game.controller;

import cat.itacademyS5_01.game.dto.GameRequest;
import cat.itacademyS5_01.game.dto.GameResponse;
import cat.itacademyS5_01.gameplay.service.GamePlayService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GamePlayService gamePlayService;

    public GameController(GamePlayService gamePlayService) {
        this.gamePlayService = gamePlayService;
    }

    @PostMapping("/new")
    public Mono<GameResponse> startNewGame(@RequestBody GameRequest gameRequest) {
        return gamePlayService.startGame(gameRequest);
    }
}
