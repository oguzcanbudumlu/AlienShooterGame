package alienshooterserver.alienshooterserverside.game;

import alienshooterserver.alienshooterserverside.player.Player;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

@Entity
public class Game {

    @Id
    private Long gameId;
    private Long score;
    private Date date;
    @ManyToOne
    private Player player;

    public Game() {}

    public Game(Long gameId, Long score, Date date, Player player) {
        this.gameId = gameId;
        this.score = score;
        this.date = date;
        this. player = player;
    }

    public Long getGameId() {return gameId;}

    public Long getScore() {return score;}

    public Date getDate() {return date;}

    public Player getPlayer() {return player;}

    public void setGameId(Long gameId) {this.gameId = gameId;}

    public void setScore(Long score) {this.score = score;}

    public void setDate(Date date) {this.date = date;}

    public void setPlayer(Player player) {this.player = player;}
}
