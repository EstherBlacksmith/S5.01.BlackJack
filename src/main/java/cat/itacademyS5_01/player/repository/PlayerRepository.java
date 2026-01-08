package cat.itacademyS5_01.player.repository;

import cat.itacademyS5_01.player.model.Player;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PlayerRepository extends ReactiveMongoRepository<Player, Integer> {
    Mono<Player> findByName(String name);
}
