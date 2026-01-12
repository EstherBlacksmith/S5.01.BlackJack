package cat.itacademyS5_01.player.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("players")
public class Player {
    @Getter
    private final String name;
    @Getter
    @Setter
    @Id
    private Long id;

    public Player(String name) {
        this.name = name;
    }

}
