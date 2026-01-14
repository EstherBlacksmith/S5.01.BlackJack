package cat.itacademyS5_01.game.strategy;

import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.game.model.Wager;
import cat.itacademyS5_01.game.service.GameService;
import reactor.core.publisher.Mono;

public class DoubleDownStrategy implements PlayerActionStrategy {
    public DoubleDownStrategy(GameService gameService) {
    }

    @Override
    public Mono<Game> execute(Game game, Wager wager) {
        return null;
    }
}
