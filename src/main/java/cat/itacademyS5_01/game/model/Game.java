package cat.itacademyS5_01.game.model;

import cat.itacademyS5_01.game.dto.PlayerResult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "games")
public class Game {
    @Setter
    @Getter
    @Id
    private String id;

    @Getter
    @Setter
    private int playerScore;
    @Getter
    @Setter
    private int bankScore;
    @Getter
    @Setter

    private String playerName;

    public Game() {
    }

    public Game(String playerName) {
        this.playerName = playerName;
        this.playerScore = 0;
        this.bankScore = 0;
    }

    public void setResult(PlayerResult result) {
    }
}
