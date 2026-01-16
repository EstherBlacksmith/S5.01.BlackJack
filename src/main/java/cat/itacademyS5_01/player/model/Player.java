package cat.itacademyS5_01.player.model;

import cat.itacademyS5_01.player.dto.Name;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("players")
public class Player {
    @Getter
    @Setter
    private Name name;
    @Getter
    @Id
    private UUID id;

    @Getter
    public int gamesWon;

    @Getter
    public int gamesLost;

    @Getter
    public int gamesTied;

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
