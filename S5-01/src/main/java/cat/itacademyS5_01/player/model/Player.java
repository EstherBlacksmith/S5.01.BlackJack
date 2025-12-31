package cat.itacademyS5_01.player.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "players")
public class Player {

    @Id
    private int id;

    private String name;
}
