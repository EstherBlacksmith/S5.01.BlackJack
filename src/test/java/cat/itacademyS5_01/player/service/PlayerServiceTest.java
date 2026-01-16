package cat.itacademyS5_01.player.service;

import cat.itacademyS5_01.exception.PlayerAlreadyExistsException;
import cat.itacademyS5_01.player.dto.Name;
import cat.itacademyS5_01.player.model.Player;
import cat.itacademyS5_01.player.repository.PlayerReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private  PlayerReactiveRepository playerReactiveRepository;

    private  PlayerService playerService;

    @BeforeEach
    void setUp(){
         playerService = new PlayerService(playerReactiveRepository);

    }
    @Test
    void givenExistingName_whenFindByName_thenReturnPlayer() {
        Name name = new Name("Pepe");
        Player player = new Player(name );

        when(playerReactiveRepository.findByName(name)).thenReturn(Mono.just(player));

        StepVerifier.create(playerService.findByName(name))
                .assertNext(foundPlayer -> {
                    assertEquals(new Name("Pepe"), foundPlayer.getName());
                })
                .expectComplete()
                .verify();
    }

    @Test
    void givenNonExistingName_whenFindByName_thenReturnError() {
        Name nonExistingName = new Name("NonExistent");

        when(playerReactiveRepository.findByName(nonExistingName)).thenReturn(Mono.empty());

        StepVerifier.create(playerService.findByName(nonExistingName))
                .expectErrorMatches(throwable ->
                        throwable instanceof RuntimeException &&
                                throwable.getMessage().contains("Player not found with name: " + nonExistingName))
                .verify();
    }


    @Test
    void givenExistingName_whenCreate_thenReturnPlayerAlreadyExistsError() {

        Name existingName = new Name("Pepe");
        Player existingPlayer = new Player(existingName);

        when(playerReactiveRepository.findByName(existingName)).thenReturn(Mono.just(existingPlayer));
        when(playerReactiveRepository.save(any(Player.class))).thenReturn(Mono.empty());

        StepVerifier.create(playerService.create(existingName))
                .expectErrorMatches(throwable ->
                        throwable instanceof PlayerAlreadyExistsException &&
                                throwable.getMessage().contains("Player already exists with name: " + existingName))
                .verify();
    }

    @Test
    void givenNewName_whenCreate_thenReturnSuccess() {

        Name newName = new Name("NewPlayer");

        when(playerReactiveRepository.findByName(newName)).thenReturn(Mono.empty());
        when(playerReactiveRepository.save(any(Player.class))).thenReturn(Mono.empty());

        StepVerifier.create(playerService.create(newName))
                .expectComplete()
                .verify();
    }



}
