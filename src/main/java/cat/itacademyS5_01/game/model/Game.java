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
    private int gamesWon;

    @Getter
    private int gamesLost;

    @Getter
    private int gamesTied;

    @Getter
    @Setter
    private String playerName;

    public Game() {
    }

    public Game(String playerName) {
        this.playerName = playerName;
        this.gamesWon = 0;
        this.gamesLost = 0;
        this.gamesTied = 0;
    }

    public void setResult(PlayerResult result) {
        PlayerResult.TIE
                PlayerResult.LOSE
                        PlayerResult.WIN
                                PlayerResult.BLACKJACK
                                        PlayerResult.PUSH
    }
}
