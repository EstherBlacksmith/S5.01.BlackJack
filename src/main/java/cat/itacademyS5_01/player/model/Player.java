package cat.itacademyS5_01.player.model;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "players")
public class Player {

    private final String name;
    @Id
    private int id;

    public Player(String name) {
        this.name = name;
    }

}
