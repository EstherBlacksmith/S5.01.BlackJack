package cat.itacademyS5_01.player.service;

import cat.itacademyS5_01.exception.MissingNameException;
import cat.itacademyS5_01.exception.PlayerAlreadyExistsException;
import cat.itacademyS5_01.player.model.Player;
import cat.itacademyS5_01.player.repository.PlayerReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlayerService {
    private final PlayerReactiveRepository playerReactiveRepository;

    public PlayerService(PlayerReactiveRepository repository) {
        this.playerReactiveRepository = repository;
    }

    public Mono<Player> create(String name) {
        if (name == null || name.trim().isEmpty()) {
            return Mono.error(new MissingNameException("Name cannot be empty"));
        }
        return playerReactiveRepository.findByName(name)
                .<Player>flatMap(existingPlayer -> Mono.error(new PlayerAlreadyExistsException("Player already exists with name: " + name)))
                .switchIfEmpty(playerReactiveRepository.save(new Player(name)));
    }

    public Mono<Player> getById(Long id) {
        return playerReactiveRepository.findById(id);
    }

    public Mono<Player> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return Mono.error(new MissingNameException("Missing name"));
        }
        return playerReactiveRepository.findByName(name)
                .switchIfEmpty(Mono.error(new RuntimeException("Player not found with name: " + name)));
    }

    public Flux<Player> getAll() {
        return playerReactiveRepository.findAll();
    }
}
