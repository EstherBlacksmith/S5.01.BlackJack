package cat.itacademyS5_01.player.service;

import cat.itacademyS5_01.game.dto.PlayerResult;
import cat.itacademyS5_01.player.model.Player;
import cat.itacademyS5_01.player.repository.PlayerReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PlayerStatsServiceImpl implements PlayerStatsService {
    private final PlayerReactiveRepository playerReactiveRepository;

    public PlayerStatsServiceImpl(PlayerReactiveRepository playerReactiveRepository) {
        this.playerReactiveRepository = playerReactiveRepository;
    }

    @Override
    public Mono<Player> updatePlayerStats(String playerName, PlayerResult result) {
        return playerReactiveRepository.findByName(playerName)
                .flatMap(player -> {
                    switch (result) {
                        case WIN:
                            player.incrementGameWon();
                            break;
                        case LOSE:
                            player.incrementGameLost();
                            break;
                        case TIE:
                            player.incrementGamesTied();
                            break;
                    }
                    return playerReactiveRepository.save(player);
                });
    }

}
