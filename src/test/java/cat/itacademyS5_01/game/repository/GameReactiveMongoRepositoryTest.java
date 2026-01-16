package cat.itacademyS5_01.game.repository;

import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.player.dto.Name;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameReactiveMongoRepositoryTest {

    @Mock
    private GameRepository gameReactiveMongoRepository;

    @Test
    public void givenValue_whenFindAllByPlayerName_thenFindAccount() {
        Game game = new Game(new Name("Pepe"));
        UUID uuid = UUID.randomUUID();
        game.setId(uuid);

        when(gameReactiveMongoRepository.save(any(Game.class))).thenReturn(Mono.just(game));
        when(gameReactiveMongoRepository.findAllByPlayerName(new Name("Pepe"))).thenReturn(Flux.just(game));


        StepVerifier.create(gameReactiveMongoRepository.save(game))
                .assertNext(savedGame -> {
                    assertEquals(new Name("Pepe"), savedGame.getPlayerName());
                    assertEquals(uuid, savedGame.getId());
                })
                .expectComplete()
                .verify();


        StepVerifier.create(gameReactiveMongoRepository.findAllByPlayerName(new Name("Pepe")))
                .assertNext(foundGame -> {
                    assertEquals(new Name("Pepe"), foundGame.getPlayerName());
                    assertEquals(uuid, foundGame.getId());
                })
                .expectComplete()
                .verify();
    }

}