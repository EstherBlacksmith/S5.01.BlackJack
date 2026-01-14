package cat.itacademyS5_01.game.strategy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PlayerActionStrategyConfig {
    @Bean
    @Qualifier("hitStrategy")
    public PlayerActionStrategy hitStrategy() {
        return game -> {
            // Logic to handle the hit action
            // For example, add a card to the player's hand and update the game state
        };
    }

    @Bean
    @Qualifier("standStrategy")
    public PlayerActionStrategy standStrategy() {
        return game -> {
            // Logic to handle the stand action
            // For example, determine the winner and update the game state
        };
    }

    @Bean
    @Qualifier("doubleDownStrategy")
    public PlayerActionStrategy doubleDownStrategy() {
        return game -> {
            // Logic to handle the double down action
            // For example, double the bet, add a card to the player's hand, and update the game state
        };
    }
}