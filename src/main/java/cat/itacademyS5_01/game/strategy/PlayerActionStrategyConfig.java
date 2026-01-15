package cat.itacademyS5_01.game.strategy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class PlayerActionStrategyConfig {
    @Bean
    @Qualifier("hitStrategy")
    public PlayerActionStrategy hitStrategy() {
        return (game, wager) -> {

            int newCard = drawCard();
            game.addCardToPlayer(newCard);

            if (game.getPlayerScore() > 21) {
                game.setGameOver(true);
                game.setWinner("Dealer");
            }

            return Mono.just(game);
        };
    }

    @Bean
    @Qualifier("standStrategy")
    public PlayerActionStrategy standStrategy() {
        return (game, wager) -> {
            game.determineWinner();
            game.setGameOver(true);

            return Mono.just(game);
        };
    }

    @Bean
    @Qualifier("doubleDownStrategy")
    public PlayerActionStrategy doubleDownStrategy() {
        return (game, wager) -> {
            game.doublePlayerWager();

            int newCard = drawCard();
            game.addCardToPlayer(newCard);

            game.determineWinner();
            game.setGameOver(true);

            return Mono.just(game);
        };
    }

    private int drawCard() {

        List<String> hearts = printSuits();
        List<String> diamonds = printSuits();
        List<String> spades = printSuits();
        List<String> clubs = printSuits();


return 0;

//TODO: acabar de implemtar la logica de la baraja y de las cartas
/*En el juego de Blackjack, una baraja estándar tiene las siguientes características:

Número de Cartas: Una baraja estándar tiene 52 cartas. Esto incluye 13 cartas de cada palo.

Palos: Hay cuatro palos en una baraja:

Corazones (Hearts)
Diamantes (Diamonds)
Tréboles (Clubs)
Picas (Spades)
Valores de las Cartas: Cada palo tiene las siguientes cartas:

Números del 2 al 10.
Figuras: J (Jota), Q (Reina), K (Rey).
As (Ace).
Valores en Blackjack:

Las cartas del 2 al 10 valen su valor nominal.
Las figuras (J, Q, K) valen 10.
El As puede valer 1 u 11, dependiendo de lo que sea más favorable para el jugador.
En el contexto del método drawCard(),
      es importante considerar estos detalles para asegurar que las cartas se generen correctamente
      y que su valor se calcule según las reglas del Blackjack.*/
    }

    private  List<String> printSuits(){
        List<String> suit = new ArrayList<>(13);

        for (int i = 1; i < 14; i++) {
            if(i == 1){
                suit.add("Ace");
            }
            if(i == 11){
                suit.add("J");
            }
            if(i == 12){
                suit.add("Q");
            }
            if(i == 13){
                suit.add("K");
            }
            suit.add(String.valueOf(i));
        }
        return suit;
    }
}