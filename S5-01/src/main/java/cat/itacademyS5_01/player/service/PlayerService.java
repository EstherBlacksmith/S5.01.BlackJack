package cat.itacademyS5_01.player.service;

import cat.itacademyS5_01.player.model.Player;
import cat.itacademyS5_01.player.repository.PlayerRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PlayerService {
    private final PlayerRepository repository;

    public PlayerService(PlayerRepository repository) {
        this.repository = repository;
    }

    public Mono<Player> create(String name) {
        Player player = new Player(name);
        return repository.save(player);
    }

    public Mono<Player> getById(int id) {
        return repository.findById(id);
    }
    public Mono<Player> findByName(String name) {
        return repository.findByName(name);
    }
    public Flux<Player> getAll() {
        return repository.findAll();
    }
}
