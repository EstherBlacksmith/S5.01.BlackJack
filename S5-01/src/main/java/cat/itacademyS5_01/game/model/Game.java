package cat.itacademyS5_01.game.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@Data
@Document("games")
public class Game {
    @Id
    private int id;

    private int playerScore;
    private int bankScore;
    private String playerName;

    public Game(String playerName) {
        this.playerName = playerName;
        this.playerScore = 0;
        this.bankScore = 0;
    }

}
