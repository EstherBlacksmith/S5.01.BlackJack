package cat.itacademyS5_01.player.controller;

import cat.itacademyS5_01.exception.MissingIdentifierException;
import cat.itacademyS5_01.exception.MissingNameException;
import cat.itacademyS5_01.player.dto.Name;
import cat.itacademyS5_01.player.dto.PlayerRequest;
import cat.itacademyS5_01.player.model.Player;
import cat.itacademyS5_01.player.service.PlayerService;
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
    public Mono<Player> newPlayer(@RequestBody PlayerRequest playerRequest) {

        return playerService.create(playerRequest.name());
    }

    @GetMapping("/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Player> getPlayer(@PathVariable String playerId) throws MissingIdentifierException {
        if (playerId == null || playerId.isEmpty()) {
            throw new MissingIdentifierException("Missing identification");
        }
        UUID id = UUID.fromString(playerId);

        return playerService.getById(id);
    }

    @PutMapping("/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Player> updatePlayerName(@PathVariable String playerId,
                                         @RequestBody Name newPlayerName) throws MissingIdentifierException {
        if (playerId == null || playerId.isEmpty()) {
            throw new MissingIdentifierException("Missing identification");
        }

        UUID id = UUID.fromString(playerId);

        return playerService.updatePlayerName(id,newPlayerName);
    }

    @GetMapping("/ping")
    public Mono<String> ping() {
        return Mono.just("ok");
    }
}