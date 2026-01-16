package cat.itacademyS5_01.player.repository;

import cat.itacademyS5_01.player.dto.Name;
import cat.itacademyS5_01.player.model.Player;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface PlayerReactiveRepository extends ReactiveCrudRepository<Player, UUID> {
    Mono<Player> findByName(Name name);
    Flux<Player> findAllByOrderByGamesWonDesc();

}
