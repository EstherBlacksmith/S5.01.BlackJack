package cat.itacademyS5_01.game.model;

import cat.itacademyS5_01.game.dto.PlayerResult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "games")
public class Game {

    @Setter
    @Getter
    @Id
    private UUID id;

    @Getter
    @Setter
    private String playerName;

    @Getter
    @Setter
    private int currentPlayerScore;

    @Getter
    @Setter
    private int currentBankScore;

    @Getter
    @Setter
    private PlayerResult result;
    
    @Getter
    @Setter
    private boolean gameOver;

    @Setter
    private String winner;


    public Game() {
        this.id = UUID.randomUUID();
    }

    public Game(String playerName) {
        this.id = UUID.randomUUID();
        this.playerName = playerName;
        this.currentPlayerScore = 0;
        this.currentBankScore = 0;
    }

    public void determineWinner() {
        if (currentPlayerScore > 21) {
            setResult(PlayerResult.LOSE);
            incrementGameLost();
        } else if (currentBankScore > 21) {
            setResult(PlayerResult.WIN);
            incrementGameWon();
        } else if (currentPlayerScore > currentBankScore) {
            setResult(PlayerResult.WIN);
            incrementGameWon();
        } else if (currentBankScore > currentPlayerScore) {
            setResult(PlayerResult.LOSE);
            incrementGameLost();
        } else {
            setResult(PlayerResult.TIE);
            incrementGamesTied();
        }
    }



    public int getPlayerScore() {
        return currentPlayerScore;
    }

    public void doublePlayerWager() {
    }

    public void addCardToPlayer(int newCard) {
    }


}

