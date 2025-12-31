package cat.itacademyS5_01.game.repository;

import cat.itacademyS5_01.game.model.Game;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface GameRepository extends ReactiveCrudRepository<Game, String> {
    Flux<Game> findAllByValue(String value);

    Mono<Game> findFirstByPlayer(Mono<String> player);

}
