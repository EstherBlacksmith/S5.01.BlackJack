package cat.itacademyS5_01.game.repository;

import cat.itacademyS5_01.game.model.MongoGame;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

/**
 * Reactive MongoDB Repository for Game documents
 */
public interface MongoGameRepository extends ReactiveMongoRepository<MongoGame, String> {
    
    /**
     * Find game by ID
     */
    Mono<MongoGame> findById(String id);
    
    /**
     * Find games by player ID
     */
    reactor.core.publisher.Flux<MongoGame> findByPlayerId(Long playerId);
    
    /**
     * Find games by game ended status
     */
    reactor.core.publisher.Flux<MongoGame> findByGameEnded(boolean gameEnded);
    
    /**
     * Delete games by player ID
     */
    Mono<Void> deleteByPlayerId(Long playerId);
}