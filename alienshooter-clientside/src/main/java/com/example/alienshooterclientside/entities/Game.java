package com.example.alienshooterclientside.entities;
import java.time.LocalDate;

/*
 * An object of Game class is created everytime a player
 * plays a game. It keeps an id, a score achieved by the player,
 * a date which show what day the game is played, and a player as a foreign key
 * who played the game.
 * We are planning to create game objects everytime whenever the players finish the game.
 */

public class Game {

    private Long gameId;

    private Long score;

    private LocalDate date;

    private Player player;

    //Constructors
    public Game() {}

    public Game(Long gameId, Long score, LocalDate date, Player player) {
        this.gameId = gameId;
        this.score = score;
        this.date = date;
        this. player = player;
    }

    public Game(Long score, LocalDate date, Player player) {
        this.score = score;
        this.date = date;
        this. player = player;
    }

    //Getters and setters
    public Long getGameId() {return gameId;}

    public Long getScore() {return score;}

    public LocalDate getDate() {return date;}

    public Player getPlayer() {return player;}

    public void setGameId(Long gameId) {this.gameId = gameId;}

    public void setScore(Long score) {this.score = score;}

    public void setDate(LocalDate date) {this.date = date;}

    public void setPlayer(Player player) {this.player = player;}
}