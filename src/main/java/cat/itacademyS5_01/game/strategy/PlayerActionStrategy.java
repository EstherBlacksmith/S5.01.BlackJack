package cat.itacademyS5_01.game.strategy;

import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.game.model.Wager;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface PlayerActionStrategy {
    Mono<Game> execute (Game game, Wager wager);
}
