package cat.itacademyS5_01.game.service;

import cat.itacademyS5_01.game.dto.MoveRequest;
import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.game.model.GameId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GameService {
    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    public Mono<Game> findById(GameId gameId) {
        return reactiveMongoTemplate.findById(gameId, Game.class);
    }

    public Flux<Game> findAll() {
        return reactiveMongoTemplate.findAll(Game.class);
    }

    public Mono<Game> save(Game game) {
        long gameId = GameIdGenerator.generateId();
        game.setId(String.valueOf(gameId));
        return reactiveMongoTemplate.save(game);
    }

    public void newMove(GameId gameId,MoveRequest moveRequest) {
        return reactiveMongoTemplate.findById(gameId, Game.class)
                .flatMap(game -> {
                    // Lógica para procesar el movimiento
                });
    }
}
