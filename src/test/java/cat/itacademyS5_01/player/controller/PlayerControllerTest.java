package cat.itacademyS5_01.player.controller;

import cat.itacademyS5_01.player.dto.Name;
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

import java.util.UUID;

@WebFluxTest(PlayerController.class)
class PlayerControllerTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private PlayerService playerService;

    @Test
    void newPlayerIsCreatedReturnSuccess() {

        Player player = new Player(new Name("Alice"));
        Mockito.when(playerService.create(new Name("Alice")))
                .thenReturn(Mono.just(player));

        webTestClient.post()
                .uri("/players/new")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new PlayerRequest(new Name("Alice")))
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.name.name").isEqualTo("Alice")  // Access nested name property
                .jsonPath("$.gamesWon").isEqualTo(0)
                .jsonPath("$.gamesLost").isEqualTo(0)
                .jsonPath("$.gamesTied").isEqualTo(0);
        Mockito.verify(playerService).create(new Name("Alice"));
    }

    @Test
    void playerIsDeletedReturnSuccess() {

        UUID uuid = UUID.randomUUID();

        Player player = new Player(new Name("Alice"));
        Mockito.when(playerService.create(new Name("Alice")))
                .thenReturn(Mono.just(player));

        Mockito.when(playerService.deletePlayer(uuid))
                .thenReturn(Mono.just(player));

        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder.path("/players/{id}").build(uuid))
                .exchange()
                .expectStatus().isOk();

        Mockito.verify(playerService).deletePlayer(uuid);
    }



    @Test
    void playerFindByIdReturnThePlayer() {

        UUID uuid = UUID.randomUUID();
        Name name = new Name("Paquita");
        Player player = new Player(name);

        Mockito.when(playerService.getById(uuid))
                .thenReturn(Mono.just(player));

        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/players/{id}").build(uuid))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.name.name").isEqualTo("Paquita");


        Mockito.verify(playerService).getById(uuid);
    }

    @Test
    void playerUpdateNameReturnThePlayer() {

        UUID uuid = UUID.randomUUID();
        Name name = new Name("Paquita");
        Player player = new Player(name);

        Mockito.when(playerService.updatePlayerName(uuid,name))
                .thenReturn(Mono.just(player));

        Name newName = new Name("Paquita Salas");
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder.path("/players/{id}").build(uuid))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(newName)
                .exchange();


        Mockito.verify(playerService).updatePlayerName(uuid,newName);
    }
}