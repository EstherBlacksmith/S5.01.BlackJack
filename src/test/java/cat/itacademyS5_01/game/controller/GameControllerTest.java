package cat.itacademyS5_01.game.controller;

import cat.itacademyS5_01.game.dto.GameRequest;
import cat.itacademyS5_01.game.dto.GameResponse;
import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.game.model.GameId;
import cat.itacademyS5_01.game.service.GameService;
import cat.itacademyS5_01.betting.service.BettingService;
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
        gameRequest = new GameRequest("Alice");
        gameResponse = new GameResponse("123L", "Alice", 0, 0);
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
                .uri("/games/{id}", " ")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @DisplayName("Returns 200 OK and the game when a valid game ID is provided")
    void getGame_Returns302AndGameWhenValidIdProvided() {
        Game mockGame = new Game("Alice");
        mockGame.setId("test-game-id-123");
        mockGame.setGamesWon(15);
        mockGame.setGamesLost(12);

        Mockito.when(gameService.findById(new GameId("test-game-id-123")))
                .thenReturn(Mono.just(mockGame));

        webTestClient.get()
                .uri("/games/{id}", "test-game-id-123")
                .exchange()
                .expectStatus().isOk()
                .expectBody(Game.class)
                .consumeWith(response -> {
                    Game returnedGame = response.getResponseBody();
                    assert returnedGame != null;
                    assert returnedGame.getId().equals("test-game-id-123");
                    assert returnedGame.getPlayerName().equals("Alice");
                    assert returnedGame.getGamesWon() == 15;
                    assert returnedGame.getGamesLost() == 12;
                });
    }

    @Test
    @DisplayName("Returns 201 if the game is created")
    void getGame_Returns201IfFounded() {
        Game mockGame = new Game("Alice");
        mockGame.setId("1");
        mockGame.setGamesWon(0);
        mockGame.setGamesLost(0);

        Mockito.when(bettingService.startGame(Mockito.any(GameRequest.class)))
                .thenReturn(Mono.just(new GameResponse("1", "Alice", 0, 0)));

        webTestClient.post()
                .uri("/games/new")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(gameRequest)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(GameResponse.class)
                .isEqualTo(new GameResponse("1", "Alice", 0, 0));
    }

}