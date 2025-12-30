package cat.itacademyS5_01.model.game;


import jakarta.persistence.*;

@Entity
@Table(schema = "games")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int playerResult;

    private int bankResult;

    private int playerId;
}
