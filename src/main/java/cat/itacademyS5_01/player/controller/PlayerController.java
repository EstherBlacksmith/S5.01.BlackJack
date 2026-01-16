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
    public Mono<Player> newPlayer(@Valid @RequestBody PlayerRequest playerRequest) {

        return playerService.create(playerRequest.name());
    }

    @GetMapping("/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Player> getPlayer(@Valid @PathVariable UUID playerId) throws MissingIdentifierException {
          return playerService.getById(playerId);
    }

    @PutMapping("/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Player> updatePlayerName(@Valid @PathVariable UUID playerId,
                                         @Valid @RequestBody Name newPlayerName) throws MissingIdentifierException {

        return playerService.updatePlayerName(playerId,newPlayerName);
    }


    @DeleteMapping("/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Player> deletePlayerById(@PathVariable UUID playerId) throws MissingIdentifierException {
        return playerService.deletePlayer(playerId);
    }

    @GetMapping("/ping")
    public Mono<String> ping() {
        return Mono.just("ok");
    }
}