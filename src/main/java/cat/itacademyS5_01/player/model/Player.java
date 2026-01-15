package cat.itacademyS5_01.player.model;

import cat.itacademyS5_01.player.dto.Name;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("players")
public class Player {
    @Getter
    private final Name name;
    @Getter
    @Id
    private UUID id;

    @Getter
    private int gamesWon;

    @Getter
    private int gamesLost;

    @Getter
    private int gamesTied;

    public Player(Name name) {
        this.name = name;
        this.gamesWon = 0;
        this.gamesLost = 0;
        this.gamesTied = 0;
    }
    public void incrementGamesTied() {
        this.gamesTied++;
    }

    public void incrementGameLost() {
        this.gamesLost++;
    }

    public void incrementGameWon() {
        this.gamesWon++;
    }
}
