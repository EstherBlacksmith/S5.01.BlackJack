package cat.itacademyS5_01.game.repository;

import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.player.dto.Name;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public class GameRepositoryImpl implements GameRepository {
    private final ReactiveMongoTemplate reactiveMongoTemplate;

    public GameRepositoryImpl(ReactiveMongoTemplate reactiveMongoTemplate) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    @Override
    public Flux<Game> findAllByPlayerName(Name playerName) {
        return reactiveMongoTemplate
                .find(Query.query(Criteria.where("playerName")
                        .is(playerName)), Game.class);
    }

    @Override
    public Mono<Game> findById(UUID gameId) {
        return reactiveMongoTemplate.findById(gameId, Game.class);
    }

    @Override
    public Flux<Game> findAll() {
        return reactiveMongoTemplate.findAll(Game.class);
    }

    @Override
    public Mono<Game> save(Game game) {
        return reactiveMongoTemplate.save(game);
    }

    @Override
    public Mono<Void> deleteById(UUID gameId) {
        Query query = Query.query(Criteria.where("_id").is(gameId));

        return reactiveMongoTemplate.remove(query, Game.class).then();
    }
}
