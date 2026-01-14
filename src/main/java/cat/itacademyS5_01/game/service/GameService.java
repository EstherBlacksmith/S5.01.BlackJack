package cat.itacademyS5_01.game.service;

import cat.itacademyS5_01.game.dto.MoveRequest;
import cat.itacademyS5_01.game.dto.PlayerResult;
import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.game.model.GameId;
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

    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    public GameService( @Qualifier("hitStrategy") PlayerActionStrategy hitStrategy,
                        @Qualifier("standStrategy") PlayerActionStrategy standStrategy,
                        @Qualifier("doubleDownStrategy") PlayerActionStrategy doubleDownStrategy) {
        this.hitStrategy = hitStrategy;
        this.standStrategy = standStrategy;
        this.doubleDownStrategy = doubleDownStrategy;
    }

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
    private void determineWinner(Game game) {
        int playerScore = game.getGamesWon();
        int bankScore = game.getGamesLost();

        if (playerScore > 21) {
            game.setResult(PlayerResult.LOSE);
            game.setGamesWon(0);
        } else if (bankScore > 21) {
            game.setResult(PlayerResult.WIN);
        } else if (playerScore > bankScore) {
            game.setGamesWon(1);
            game.setResult(PlayerResult.WIN);
        } else if (bankScore > playerScore) {
            game.setGamesWon(0);
            game.setResult(PlayerResult.LOSE);
        } else {
            // Empate
            game.setResult(PlayerResult.TIE);
        }
//TODO:implement logic to increment the wins in the scores
    }

}
