package com.example.alienshooterclientside.Entities;

import com.example.alienshooterclientside.Utilities.Constants;

/**
 * Player class is used like accounts for the game.
 */
public class Player {
    private Long playerId = Constants.DEFAULT_PLAYER_ID;
    private String nickname;
    private String password;

    //Constructors
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

    //Getters and Setters
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