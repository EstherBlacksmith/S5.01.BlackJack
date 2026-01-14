package cat.itacademyS5_01.game.repository;

import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.game.model.GameId;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface GameReactiveMongoRepository extends ReactiveCrudRepository<Game, GameId> {

    Flux<Game> findAllByPlayerName(String playerName);
    Mono<Game> findById(GameId id);
    Mono<Game> save(Game game);
    Mono<Void> deleteById(GameId id);
}
