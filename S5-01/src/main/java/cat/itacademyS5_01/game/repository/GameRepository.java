package cat.itacademyS5_01.game.repository;

import cat.itacademyS5_01.game.model.Game;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface GameRepository  extends ReactiveMongoRepository<Game,String> {
    Flux<Game> findAllByValue(String value);
    Mono<Game> findFirstByPlayer(Mono<String> player);
    /*int playerId=0;
    Flux<Game> gameFlux = repository.findAll(Example.of(new Game( playerId)));
    //https://www.baeldung.com/spring-data-mongodb-reactive*/
}
