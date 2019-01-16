package com.example.alienshooterclientside.entities;

/*
 * Score class is used when there is a request to show
 * leaderboard, it provides the player's name and score.
 */
public class Score {
    private int ranking;
    private String name;
    private long score;

    Score() {}

    public Score(int ranking, String name, long score) {
        this.ranking = ranking;
        this.name = name;
        this.score = score;
    }

    public int getRanking() {return ranking;}

    public void setRanking(int ranking) {this.ranking = ranking;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
