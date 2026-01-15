package cat.itacademyS5_01.player.service;

import cat.itacademyS5_01.exception.MissingNameException;
import cat.itacademyS5_01.exception.PlayerAlreadyExistsException;
import cat.itacademyS5_01.player.dto.Name;
import cat.itacademyS5_01.player.model.Player;
import cat.itacademyS5_01.player.repository.PlayerReactiveRepository;
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
    private PlayerReactiveRepository playerRepository;

    @Test
    void givenExistingName_whenFindByName_thenReturnPlayer() {
        PlayerService playerService = new PlayerService(playerRepository);
        Player player = new Player(new Name("Pepe"));

        when(playerRepository.findByName(new Name("Pepe"))).thenReturn(Mono.just(player));

        StepVerifier.create(playerService.findByName(new Name("Pepe"))
                .assertNext(foundPlayer -> {
                    assertEquals(new Name("Pepe"), foundPlayer.getName());
                })
                .expectComplete()
                .verify();
    }

    @Test
    void givenNonExistingName_whenFindByName_thenReturnError() {
        PlayerService playerService = new PlayerService(playerRepository);
        Name nonExistingName = new Name("NonExistent");

        when(playerRepository.findByName(nonExistingName)).thenReturn(Mono.empty());

        StepVerifier.create(playerService.findByName(nonExistingName))
                .expectErrorMatches(throwable ->
                        throwable instanceof RuntimeException &&
                                throwable.getMessage().contains("Player not found with name: " + nonExistingName))
                .verify();
    }

    @Test
    void givenEmptyName_whenFindByName_thenReturnError() {
        PlayerService playerService = new PlayerService(playerRepository);
        String emptyName = "";

        // NO stubbing needed - service validates empty name BEFORE calling repository
        StepVerifier.create(playerService.findByName(emptyName))
                .expectErrorMatches(throwable ->
                        throwable instanceof MissingNameException &&
                                throwable.getMessage().contains("Missing name"))
                .verify();
    }

    @Test
    void givenExistingName_whenCreate_thenReturnPlayerAlreadyExistsError() {
        PlayerService playerService = new PlayerService(playerRepository);
        Name existingName = new Name("Pepe");
        Player existingPlayer = new Player(existingName);

        // Mock that player already exists
        when(playerRepository.findByName(existingName)).thenReturn(Mono.just(existingPlayer));
        // Mock save to avoid NullPointerException in switchIfEmpty (even though it shouldn't be called)
        when(playerRepository.save(any(Player.class))).thenReturn(Mono.empty());

        StepVerifier.create(playerService.create(existingName))
                .expectErrorMatches(throwable ->
                        throwable instanceof PlayerAlreadyExistsException &&
                                throwable.getMessage().contains("Player already exists with name: " + existingName))
                .verify();
    }

    @Test
    void givenNewName_whenCreate_thenReturnSuccess() {
        PlayerService playerService = new PlayerService(playerRepository);
        Name newName = new Name("NewPlayer");

        // Mock that player does not exist
        when(playerRepository.findByName(newName)).thenReturn(Mono.empty());
        // Use any() for save to match any Player instance
        when(playerRepository.save(any(Player.class))).thenReturn(Mono.empty());

        StepVerifier.create(playerService.create(newName))
                .expectComplete()
                .verify();
    }

    @Test
    void givenEmptyName_whenCreate_thenReturnMissingNameError() {
        PlayerService playerService = new PlayerService(playerRepository);

        // NO stubbing needed - service validates empty name BEFORE calling repository
        StepVerifier.create(playerService.create(new Name("")))
                .expectErrorMatches(throwable ->
                        throwable instanceof MissingNameException &&
                                throwable.getMessage().contains("Name cannot be empty"))
                .verify();
    }
}
