package cat.itacademyS5_01.model.player;

import jakarta.persistence.*;
import org.springframework.data.annotation.Id;

@Entity
@Table(schema = "players")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(unique = true, nullable = false)
    private String name;
}
