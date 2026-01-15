package cat.itacademyS5_01.game.model;

import cat.itacademyS5_01.game.dto.PlayerResult;
import cat.itacademyS5_01.player.model.Player;
import cat.itacademyS5_01.player.service.PlayerStatsService;
import cat.itacademyS5_01.player.service.PlayerStatsServiceImpl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Document(collection = "games")
public class Game {

    @Setter
    @Getter
    @Id
    private UUID id;

    @Getter
    @Setter
    private String playerName;

    @Getter
    @Setter
    private int currentPlayerScore;

    @Getter
    @Setter
    private int currentBankScore;

    @Getter
    @Setter
    private PlayerResult result;
    
    @Getter
    @Setter
    private boolean gameOver;

    @Setter
    private String winner;


    public Game() {
        this.id = UUID.randomUUID();
    }

    public Game(String playerName) {
        this.id = UUID.randomUUID();
        this.playerName = playerName;
        this.currentPlayerScore = 0;
        this.currentBankScore = 0;
    }

    public int getPlayerScore() {
        return currentPlayerScore;
    }

    public void doublePlayerWager() {
    }

    public void addCardToPlayer(int newCard) {
    }


}

