package cat.itacademyS5_01.game.controller;

import cat.itacademyS5_01.exception.MissingNameException;
import cat.itacademyS5_01.game.dto.GameRequest;
import cat.itacademyS5_01.game.dto.GameResponse;
import cat.itacademyS5_01.gameplay.service.GamePlayService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GamePlayService gamePlayService;

    public GameController(GamePlayService gamePlayService) {
        this.gamePlayService = gamePlayService;
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<GameResponse> startNewGame(@RequestBody GameRequest gameRequest) {
        if (gameRequest.playerName().isEmpty()) {
            throw new MissingNameException("Missing name");
        }

        return gamePlayService.startGame(gameRequest);
    }
}
