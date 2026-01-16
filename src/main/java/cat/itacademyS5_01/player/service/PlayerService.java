package cat.itacademyS5_01.player.service;

import cat.itacademyS5_01.exception.MissingNameException;
import cat.itacademyS5_01.exception.PlayerAlreadyExistsException;
import cat.itacademyS5_01.player.dto.Name;
import cat.itacademyS5_01.player.model.Player;
import cat.itacademyS5_01.player.repository.PlayerReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class PlayerService {
    private final PlayerReactiveRepository playerReactiveRepository;

    public PlayerService(PlayerReactiveRepository repository) {
        this.playerReactiveRepository = repository;
    }

    public Mono<Player> create(Name name) {

        return playerReactiveRepository.findByName(name)
                .<Player>flatMap(existingPlayer -> Mono.error(new PlayerAlreadyExistsException("Player already exists with name: " + name)))
                .switchIfEmpty(playerReactiveRepository.save(new Player(name)));
    }

    public Mono<Player> getById(UUID id) {
        return playerReactiveRepository.findById(id).switchIfEmpty(Mono.error(new RuntimeException("Player not found")));
    }

    public Mono<Player> findByName(Name name) {

        return playerReactiveRepository.findByName(name)
                .switchIfEmpty(Mono.error(new RuntimeException("Player not found with name: " + name)));
    }

    public Flux<Player> getAll() {
        return playerReactiveRepository.findAll();
    }

    public Mono<Player> updatePlayerName(UUID id, Name newPlayerName) {
        return getById(id)
                .flatMap(player -> {
                    player.setName(newPlayerName);
                    return playerReactiveRepository.save(player);
                });
    }

    public Mono<Player> deletePlayer(UUID id) {
        return playerReactiveRepository.findById(id)
                .flatMap(player -> playerReactiveRepository.deleteById(id)
                        .thenReturn(player));
    }


}
