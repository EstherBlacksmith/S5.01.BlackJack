package cat.itacademyS5_01.game.controller;

import cat.itacademyS5_01.exception.MissingIdentifierException;
import cat.itacademyS5_01.game.dto.GameRequest;
import cat.itacademyS5_01.game.dto.GameResponse;
import cat.itacademyS5_01.game.dto.MoveRequest;
import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.game.service.GameService;
import cat.itacademyS5_01.betting.service.BettingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/games")
public class GameController {

    private final BettingService bettingService;
    private final GameService gameService;

    public GameController(BettingService bettingService, GameService gameService) {
        this.bettingService = bettingService;
        this.gameService = gameService;
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<GameResponse> startNewGame(@Valid @RequestBody GameRequest gameRequest) {
        return bettingService.startGame(gameRequest);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Game> getGame(@PathVariable UUID gameId) throws MissingIdentifierException {

        return gameService.findById(gameId);
    }


    @PostMapping("{id}/play")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Game> makeMove(@Valid @PathVariable UUID gameId, @Valid @RequestBody  MoveRequest moveRequest) throws MissingIdentifierException {

        return bettingService.makeMove(gameId,moveRequest);
    }
}
