package cat.itacademyS5_01.game.repository;

import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.player.dto.Name;
import jakarta.validation.Valid;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface GameRepository {
    Flux<Game> findAllByPlayerName(Name playerName);

    Mono<Game> findById(UUID gameId);
    Flux<Game> findAll();
    Mono<Game> save(Game game);

    Mono<Void> deleteById(@Valid UUID gameId);
}
