package cat.itacademyS5_01.game.repository;

import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.game.model.GameId;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface GameRepository {
    Flux<Game> findAllByPlayerName(String playerName);
    Mono<Game> findById(GameId gameId);
    Flux<Game> findAll();
    Mono<Game> save(Game game);
}
