package cat.itacademyS5_01.game.controller;

import cat.itacademyS5_01.game.dto.GameRequest;
import cat.itacademyS5_01.game.dto.GameResponse;
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

    private GameRequest gameRequest;
    private GameResponse gameResponse;

    @BeforeEach
    void setUp() {
        gameRequest = new GameRequest("Alice");
        gameResponse = new GameResponse(123L, "Alice", 0, 0);
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

}