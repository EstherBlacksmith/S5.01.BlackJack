package cat.itacademyS5_01.game.repository;

import cat.itacademyS5_01.game.model.Game;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface GameReactiveMongoRepository extends ReactiveMongoRepository<Game, Integer> {

    Flux<Game> findAllByPlayerName(String playerName);
    Mono<Game> findById(Mono <Integer> playerId);

}
