package cat.itacademyS5_01.game.repository;

import cat.itacademyS5_01.game.model.Game;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GameRepositoryImpl implements GameRepository {
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public GameRepositoryImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Flux<Game> findAllByPlayerName(String playerName) {
        return null;
    }

    @Override
    public Mono<Game> findById(GameId gameId) {
        return null;
    }

    @Override
    public Flux<Game> findAll() {
        return null;
    }

    @Override
    public Mono<Game> save(Game game) {
        return null;
    }
}
