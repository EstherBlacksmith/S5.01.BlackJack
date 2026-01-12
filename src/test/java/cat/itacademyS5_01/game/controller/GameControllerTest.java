package cat.itacademyS5_01.game.controller;

import cat.itacademyS5_01.game.dto.GameRequest;
import cat.itacademyS5_01.game.dto.GameResponse;
import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.game.service.GameService;
import cat.itacademyS5_01.gameplay.service.GamePlayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest(controllers = GameController.class)
class GameControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private GamePlayService gamePlayService;

    @MockBean
    private GameService gameService;

    private GameRequest gameRequest;
    private GameResponse gameResponse;

    @BeforeEach
    void setUp() {
        gameRequest = new GameRequest("Alice");
        gameResponse = new GameResponse("123L", "Alice", 0, 0);
    }

    @Test
    @DisplayName("Returns 201 if the game is created")
    void startNewGame_Returns201IfCreated() {
        Mockito.when(gamePlayService.startGame(Mockito.any(GameRequest.class)))
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
    @DisplayName("Returns bad request if the player name is not in the request")
    void startNewGame_Returns400IfNotCreatedBecausePlayerNameMissing() {

        GameRequest missingNameRequest = new GameRequest("");

        webTestClient.post()
                .uri("/games/new")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(missingNameRequest)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("Returns bad request if the id of the game is not in the request")
    void getGame_Returns400IfNotCreatedBecauseGameIdIsMissing() {

        webTestClient.get()
                .uri("/games/game/{id}", " ")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("Returns 302 FOUND and the game when a valid game ID is provided")
    void getGame_Returns302AndGameWhenValidIdProvided() {
        // Step 1: Create a mock game that will be returned by the service
        Game mockGame = new Game("Alice");
        mockGame.setId("test-game-id-123");
        mockGame.setPlayerScore(15);
        mockGame.setBankScore(12);

        // Step 2: Mock the GameService to return our test game when findById is called
        Mockito.when(gameService.findById("test-game-id-123"))
                .thenReturn(Mono.just(mockGame));

        // Step 3: Call the GET endpoint with the game ID
        webTestClient.get()
                .uri("/games/game/{id}", "test-game-id-123")
                .exchange()
                // Step 4: Verify the response status is 302 FOUND
                .expectStatus().isFound()
                // Step 5: Verify the response body contains the correct game
                .expectBody(Game.class)
                .consumeWith(response -> {
                    Game returnedGame = response.getResponseBody();
                    // Verify the game properties match our mock game
                    assert returnedGame != null;
                    assert returnedGame.getId().equals("test-game-id-123");
                    assert returnedGame.getPlayerName().equals("Alice");
                    assert returnedGame.getPlayerScore() == 15;
                    assert returnedGame.getBankScore() == 12;
                });
    }
    @Test
    @DisplayName("Returns 201 if the game is created")
    void getGame_Returns201IfFounded() {
        // Create a mock Game object instead of using GameResponse
        Game mockGame = new Game("Alice");
        mockGame.setId("123L");
        mockGame.setPlayerScore(0);
        mockGame.setBankScore(0);

        Mockito.when(gameService.findById(Mockito.any(String.class)))
                .thenReturn(Mono.just(mockGame));

        webTestClient.post()
                .uri("/games/new")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(gameRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(GameResponse.class)
                .isEqualTo(gameResponse);
    }

}