package cat.itacademyS5_01.game.model;

import cat.itacademyS5_01.game.dto.PlayerResult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "games")
public class Game {

    @Getter
    @Id
    private String id;

    @Getter
    @Setter
    private String playerName;

    @Getter
    private int gamesWon;

    @Getter
    private int gamesLost;

    @Getter
    private int gamesTied;

    @Getter
    @Setter
    private int currentPlayerScore;

    @Getter
    @Setter
    private int currentBankScore;

    @Getter
    @Setter
    private PlayerResult result;

    public Game() {}

    public Game(String playerName) {
        this.playerName = playerName;
        this.gamesWon = 0;
        this.gamesLost = 0;
        this.gamesTied = 0;
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

    private void incrementGamesTied() {
        this.gamesTied++;
    }

    private void incrementGameLost() {
        this.gamesLost++;
    }


    private void incrementGameWon() {
        this.gamesWon++;
    }

}
