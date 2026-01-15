package cat.itacademyS5_01.player.service;

import cat.itacademyS5_01.game.dto.PlayerResult;
import cat.itacademyS5_01.player.dto.Name;
import cat.itacademyS5_01.player.model.Player;
import reactor.core.publisher.Mono;

public interface PlayerStatsService {
    Mono<Player> updatePlayerStats(Name playerName, PlayerResult result);
}
