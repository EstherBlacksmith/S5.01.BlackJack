package cat.itacademyS5_01.game.controller;

import cat.itacademyS5_01.game.dto.GameRequest;
import cat.itacademyS5_01.game.dto.GameResponse;
import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.game.service.GameService;
import cat.itacademyS5_01.betting.service.BettingService;
import cat.itacademyS5_01.player.dto.Name;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@WebFluxTest(controllers = GameController.class)
class GameControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private BettingService bettingService;

    @MockBean
    private GameService gameService;

    private GameRequest gameRequest;
    private GameResponse gameResponse;

    @BeforeEach
    void setUp() {
        Name name = new Name("Alice");
        gameRequest = new GameRequest(name);
        UUID uuid = UUID.randomUUID();
        gameResponse = new GameResponse(uuid, new Name("Alice"), 0, 0);
    }

    @Test
    @DisplayName("Returns 201 if the game is created")
    void startNewGame_Returns201IfCreated() {
        Mockito.when(bettingService.startGame(Mockito.any(GameRequest.class)))
                .thenReturn(Mono.just(gameResponse));

        webTestClient.post()
                .uri("/games/new")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(gameRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(GameResponse.class)
                .isEqualTo(gameResponse);
    }




    @Test
    @DisplayName("Returns 200 OK and the game when a valid game ID is provided")
    void getGame_Returns302AndGameWhenValidIdProvided() {
        Game mockGame = new Game(new Name("Alice"));
        mockGame.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));

        Mockito.when(gameService.findById(UUID.fromString("123e4567-e89b-12d3-a456-426614174000")))
                .thenReturn(Mono.just(mockGame));

        webTestClient.get()
                .uri("/games/{id}", "123e4567-e89b-12d3-a456-426614174000")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Game.class)
                .consumeWith(response -> {
                    Game returnedGame = response.getResponseBody();
                    assert returnedGame != null;
                    assert returnedGame.getId().equals(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
                    assert returnedGame.getPlayerName().equals(new Name("Alice"));
                });
    }

    @Test
    @DisplayName("Returns 201 if the game is created")
    void getGame_Returns201IfFounded() {
        Game mockGame = new Game(new Name("Alice"));
        mockGame.setId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));


        GameResponse alice = new GameResponse(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), new Name("Alice"), 0, 0);
        Mockito.when(bettingService.startGame(Mockito.any(GameRequest.class)))
                .thenReturn(Mono.just(alice));

        webTestClient.post()
                .uri("/games/new")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(gameRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(GameResponse.class)
                .isEqualTo(alice);
    }

}