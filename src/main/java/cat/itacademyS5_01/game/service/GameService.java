package cat.itacademyS5_01.game.service;

import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.game.repository.GameRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GameService {
    private final GameRepository gameRepository;


    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }


    public Mono<Game> findById(Integer id) {
        return gameRepository.findById(id);
    }

    public Flux<Game> findAll() {
        return gameRepository.findAll();
    }

    public Mono<Game> save(Game game) {
        return gameRepository.save(game);
    }

}
