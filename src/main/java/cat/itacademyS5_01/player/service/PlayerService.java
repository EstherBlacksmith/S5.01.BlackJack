package cat.itacademyS5_01.player.service;

import cat.itacademyS5_01.exception.MissingNameException;
import cat.itacademyS5_01.exception.PlayerAlreadyExists;
import cat.itacademyS5_01.player.model.Player;
import cat.itacademyS5_01.player.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class PlayerService {
    private final PlayerRepository repository;

    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    public Mono<Player> create(String name) {
        if (name == null || name.trim().isEmpty()) {
            return Mono.error(new MissingNameException("Name cannot be empty"));
        }
        return repository.findByName(name)
                .<Player>flatMap(existingPlayer -> Mono.error(new PlayerAlreadyExists("Player already exists with name: " + name)))
                .switchIfEmpty(repository.save(new Player(name)));
    }

    public Mono<Player> getById(int id) {
        return repository.findById(id);
    }

    public Mono<Player> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return Mono.error(new MissingNameException("Missing name"));
        }
        return repository.findByName(name)
                .switchIfEmpty(Mono.error(new RuntimeException("Player not found with name: " + name)));
    }

    public Flux<Player> getAll() {
        return repository.findAll();
    }
}
