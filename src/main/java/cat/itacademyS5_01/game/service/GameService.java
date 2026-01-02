package cat.itacademyS5_01.game.service;

import cat.itacademyS5_01.game.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GameService {
    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<Game> findById(String id) {
        return reactiveMongoTemplate.findById(id, Game.class);
    }

    public Flux<Game> findAll() {
        return reactiveMongoTemplate.findAll(Game.class);
    }

    public Mono<Game> save(Game game) {
        return reactiveMongoTemplate.save(game);
    }

}
