package cat.itacademyS5_01.game.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("games")
public class Game {
    @Id
    private int id;

    private int playerResult;

    private int bankResult;

    private int playerId;

    public Game(int playerId) {
        this.playerId = playerId;
    }

}
