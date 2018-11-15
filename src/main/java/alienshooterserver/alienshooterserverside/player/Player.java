package alienshooterserver.alienshooterserverside.player;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Player {

    @Id
    private Long playerId;
    private String nickname;
    private String password;

    public Player() {}

    public Player(long playerId, String nickname, String password) {
        super();
        this.playerId = playerId;
        this.nickname = nickname;
        this.password = password;
    }

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
