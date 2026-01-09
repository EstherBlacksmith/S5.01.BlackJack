package cat.itacademyS5_01.player.repository;

import cat.itacademyS5_01.player.model.Player;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PlayerReactiveRepository extends ReactiveCrudRepository<Player, Integer> {
    Mono<Player> findByName(String name);
}
