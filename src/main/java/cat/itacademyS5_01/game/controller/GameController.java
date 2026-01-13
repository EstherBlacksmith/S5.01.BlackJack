package cat.itacademyS5_01.game.controller;

import cat.itacademyS5_01.exception.MissingIdentifierException;
import cat.itacademyS5_01.exception.MissingNameException;
import cat.itacademyS5_01.game.dto.GameRequest;
import cat.itacademyS5_01.game.dto.GameResponse;
import cat.itacademyS5_01.game.dto.MoveRequest;
import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.game.model.GameId;
import cat.itacademyS5_01.game.service.GameService;
import cat.itacademyS5_01.gameplay.service.GamePlayService;
import jakarta.validation.Valid;
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

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Game> getGame(@PathVariable String gameId) throws MissingIdentifierException {
        GameId validatedGameId = new GameId(gameId);

        return gameService.findById(validatedGameId);
    }


    @PostMapping("{id}/play")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Game> makeMove(@PathVariable String gameId, @Valid @RequestBody  MoveRequest moveRequest) throws MissingIdentifierException {
        GameId validatedGameId = new GameId(gameId);

        return gamePlayService.makeMove(validatedGameId,moveRequest);
    }
}
