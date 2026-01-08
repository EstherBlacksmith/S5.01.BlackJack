package cat.itacademyS5_01.game.repository;

import cat.itacademyS5_01.game.model.Game;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameReactiveMongoRepositoryTest {

    @Mock
    private GameReactiveMongoRepository gameReactiveMongoRepository;

    @Test
    public void givenValue_whenFindAllByPlayerName_thenFindAccount() {
        Game game = new Game("Pepe");
        game.setId(1);
        
        when(gameReactiveMongoRepository.save(any(Game.class))).thenReturn(Mono.just(game));
        when(gameReactiveMongoRepository.findAllByPlayerName("Pepe")).thenReturn(Flux.just(game));

        // Verify save
        StepVerifier.create(gameReactiveMongoRepository.save(game))
                .assertNext(savedGame -> {
                    assertEquals("Pepe", savedGame.getPlayerName());
                    assertEquals(1, savedGame.getId());
                })
                .expectComplete()
                .verify();

        // Verify find
        StepVerifier.create(gameReactiveMongoRepository.findAllByPlayerName("Pepe"))
                .assertNext(foundGame -> {
                    assertEquals("Pepe", foundGame.getPlayerName());
                    assertEquals(1, foundGame.getId());
                })
                .expectComplete()
                .verify();
    }

}