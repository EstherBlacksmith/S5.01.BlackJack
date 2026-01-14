package cat.itacademyS5_01.game.service;

import cat.itacademyS5_01.game.dto.MoveRequest;
import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.game.model.GameId;
import cat.itacademyS5_01.game.repository.GameRepository;
import cat.itacademyS5_01.game.strategy.PlayerActionStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GameService {
    private final PlayerActionStrategy hitStrategy;
    private final PlayerActionStrategy standStrategy;
    private final PlayerActionStrategy doubleDownStrategy;
    private final GameRepository gameRepository;


    public GameService(@Qualifier("hitStrategy") PlayerActionStrategy hitStrategy,
                       @Qualifier("standStrategy") PlayerActionStrategy standStrategy,
                       @Qualifier("doubleDownStrategy") PlayerActionStrategy doubleDownStrategy, GameRepository gameRepository) {
        this.hitStrategy = hitStrategy;
        this.standStrategy = standStrategy;
        this.doubleDownStrategy = doubleDownStrategy;
        this.gameRepository = gameRepository;
    }

    public Mono<Game> findById(GameId gameId) {
        return gameRepository.findById(gameId);
    }

    public Flux<Game> findAll() {
        return gameRepository.findAll();
    }

    public Mono<Game> save(Game game) {
        long gameId = GameIdGenerator.generateId();
        game.setId(String.valueOf(gameId));
        return gameRepository.save(game);
    }

    public void newMove(GameId gameId,MoveRequest moveRequest) {
       /* return gameRepository.findById(gameId)
                .flatMap(game -> {
                    // Lógica para procesar el movimiento
                });*/
    }


}
