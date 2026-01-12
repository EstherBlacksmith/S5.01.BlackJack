package cat.itacademyS5_01.player.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("players")
public class Player {


    @Getter
    @Setter
    @Id
    private Long id;
    @Getter
    private final String name;

    public Player(String name) {
        this.name = name;
    }

    // Manual getter for name since Lombok isn't being processed
    public String getName() {
        return name;
    }

}
