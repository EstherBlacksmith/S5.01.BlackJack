package cat.itacademyS5_01.player.controller;

import cat.itacademyS5_01.exception.MissingNameException;
import cat.itacademyS5_01.player.dto.PlayerRequest;
import cat.itacademyS5_01.player.model.Player;
import cat.itacademyS5_01.player.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

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
        if (playerRequest.name().isEmpty()) {
            throw new MissingNameException("Missing name");
        }

        return playerService.create(playerRequest.name());
    }
}
