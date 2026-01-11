package cat.itacademyS5_01.player.controller;

import cat.itacademyS5_01.player.dto.PlayerRequest;
import cat.itacademyS5_01.player.model.Player;
import cat.itacademyS5_01.player.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@WebFluxTest(PlayerController.class)
class PlayerControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private PlayerService playerService;

    @Test
    void newPlayerIsCreatedReturnSuccess() {
        Player player = new Player("Alice");

        Mockito.when(playerService.create("Alice"))
                .thenReturn(Mono.just(player));

        webTestClient.post()
                .uri("/players/new")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new PlayerRequest("Alice"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.name").isEqualTo("Alice");
    }

    @Test
    void createPlayerMissingNameShouldFail() {
        webTestClient.post()
                .uri("/players/new")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new PlayerRequest(""))
                .exchange()
                .expectStatus().isBadRequest();
    }
}