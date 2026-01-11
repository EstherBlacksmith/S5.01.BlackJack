package cat.itacademyS5_01.game.controller;

import cat.itacademyS5_01.exception.MissingIdentifierException;
import cat.itacademyS5_01.exception.MissingNameException;
import cat.itacademyS5_01.game.dto.GameRequest;
import cat.itacademyS5_01.game.dto.GameResponse;
import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.game.service.GameService;
import cat.itacademyS5_01.gameplay.service.GamePlayService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GamePlayService gamePlayService;
    private final GameService gameService;
    public GameController(GamePlayService gamePlayService, GameService gameService) {
        this.gamePlayService = gamePlayService;
        this.gameService = gameService;
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<GameResponse> startNewGame(@RequestBody GameRequest gameRequest) {
        if (gameRequest.playerName().isEmpty()) {
            throw new MissingNameException("Missing name");
        }

        return gamePlayService.startGame(gameRequest);
    }



    @GetMapping("/new")
    @ResponseStatus(HttpStatus.FOUND)
    public Mono<Game> getGame(@RequestBody String gameId) throws MissingIdentifierException {
        if (gameId.isEmpty()) {
            throw new MissingIdentifierException("Missing game identifier");
        }

        return gameService.findById(gameId);
    }
}
