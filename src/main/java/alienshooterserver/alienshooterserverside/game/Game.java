package alienshooterserver.alienshooterserverside.game;

import alienshooterserver.alienshooterserverside.player.Player;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "GAME")
public class Game {

    @Id
    @Column(name = "GameId")
    private Long gameId;

    @Column(name = "Score")
    private Long score;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "Date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "playerId")
    private Player player;

    public Game() {}

    public Game(Long gameId, Long score, LocalDate date, Player player) {
        this.gameId = gameId;
        this.score = score;
        this.date = date;
        this. player = player;
    }

    public Long getGameId() {return gameId;}

    public Long getScore() {return score;}

    public LocalDate getDate() {return date;}

    public Player getPlayer() {return player;}

    public void setGameId(Long gameId) {this.gameId = gameId;}

    public void setScore(Long score) {this.score = score;}

    public void setDate(LocalDate date) {this.date = date;}

    public void setPlayer(Player player) {this.player = player;}
}
