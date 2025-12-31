package cat.itacademyS5_01.player.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Table(schema = "player")
public class Player {

    @Id
    private int id;

    private final String name;

    public Player(String name) {
        this.name = name;
    }

}
