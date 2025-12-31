package cat.itacademyS5_01.player.repository;

import cat.itacademyS5_01.player.model.Player;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository  extends ReactiveCrudRepository<Player, Integer> {
}
