package cat.itacademyS5_01.player.controller;

import cat.itacademyS5_01.exception.MissingIdentifierException;
import cat.itacademyS5_01.exception.MissingNameException;
import cat.itacademyS5_01.player.dto.Name;
import cat.itacademyS5_01.player.dto.PlayerRequest;
import cat.itacademyS5_01.player.model.Player;
import cat.itacademyS5_01.player.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/players")
public class PlayerController {
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Player> newPlayer(@RequestBody @Valid PlayerRequest playerRequest) {

        return playerService.create(playerRequest.name());
    }

    @GetMapping("/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Player> getPlayer(@PathVariable @Valid UUID playerId) throws MissingIdentifierException {
          return playerService.getById(playerId);
    }

    @PutMapping("/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Player> updatePlayerName(@PathVariable @Valid  UUID playerId,
                                         @RequestBody @Valid Name newPlayerName) throws MissingIdentifierException {

        return playerService.updatePlayerName(playerId,newPlayerName);
    }


    @DeleteMapping("/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Player> deletePlayerById(@PathVariable @Valid UUID playerId) throws MissingIdentifierException {
        return playerService.deletePlayer(playerId);
    }

    @GetMapping("/ranking")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Player> ranking() throws MissingIdentifierException {
        return playerService.ranking();
    }

}