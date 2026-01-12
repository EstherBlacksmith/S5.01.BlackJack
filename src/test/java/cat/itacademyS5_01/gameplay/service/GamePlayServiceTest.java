package cat.itacademyS5_01.gameplay.service;

import cat.itacademyS5_01.game.dto.GameRequest;
import cat.itacademyS5_01.game.dto.MoveRequest;
import cat.itacademyS5_01.game.dto.PlayerAction;
import cat.itacademyS5_01.game.model.Game;
import cat.itacademyS5_01.game.service.GameService;
import cat.itacademyS5_01.player.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GamePlayServiceTest {

    @Mock
    private PlayerService playerService;

    @Mock
    private GameService gameService;

    @InjectMocks
    private GamePlayService gamePlayService;

    @Test
    void testStartGame() {
        // Setup
        GameRequest gameRequest = new GameRequest("TestPlayer");
        Game mockGame = new Game("TestPlayer");
        mockGame.setId("game123");
        
        when(playerService.findByName(anyString())).thenReturn(Mono.empty());
        when(playerService.create(anyString())).thenReturn(Mono.just(new cat.itacademyS5_01.player.model.Player("TestPlayer")));
        when(gameService.save(any(Game.class))).thenReturn(Mono.just(mockGame));

        // Test
        StepVerifier.create(gamePlayService.startGame(gameRequest))
                .expectNextMatches(response -> 
                    response.playerName().equals("TestPlayer") &&
                    response.playerScore() == 0 &&
                    response.bankScore() == 0
                )
                .verifyComplete();
    }

    @Test
    void testMakeMove() {
        // Setup
        MoveRequest moveRequest = new MoveRequest("game123", PlayerAction.HIT, 10);
        Game mockGame = new Game("TestPlayer");
        mockGame.setId("game123");
        mockGame.setPlayerScore(15);
        mockGame.setBankScore(12);
        
        when(gameService.newMove(any(MoveRequest.class))).thenReturn(Mono.just(mockGame));

        // Test
        StepVerifier.create(gamePlayService.makeMove(moveRequest))
                .expectNextMatches(game -> 
                    game.getPlayerScore() == 15 &&
                    game.getBankScore() == 12
                )
                .verifyComplete();
    }

    @Test
    void testMakeMoveWithInvalidGame() {
        // Setup
        MoveRequest moveRequest = new MoveRequest("invalidGame", PlayerAction.HIT, 10);
        
        when(gameService.newMove(any(MoveRequest.class)))
                .thenReturn(Mono.error(new RuntimeException("Game not found")));

        // Test
        StepVerifier.create(gamePlayService.makeMove(moveRequest))
                .expectError(RuntimeException.class)
                .verify();
    }
}