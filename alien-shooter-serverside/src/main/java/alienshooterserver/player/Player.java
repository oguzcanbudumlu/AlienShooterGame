package alienshooterserver.player;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PLAYER")
public class Player {

    /* A player consists of id, nickname and password.
    Password will be saved in form of its hash.  */
    @Id
    @Column(name = "PlayerId")
    private Long playerId;
    @Column(name = "Nickname")
    private String nickname;
    @Column(name = "Password")
    private String password;


    /* If there does not exist default constructor,
    Spring cannot create object based on requests */
    public Player() {}

    public Player(long playerId, String nickname, String password) {
        super();
        this.playerId = playerId;
        this.nickname = nickname;
        this.password = password;
    }

    public Player(String nickname, String password) {
        super();
        this.nickname = nickname;
        this.password = password;
    }


    /* Getter and setter functions for attributes */
    public long getPlayerId() {
        return playerId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
