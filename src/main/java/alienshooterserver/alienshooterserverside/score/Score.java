package alienshooterserver.alienshooterserverside.score;

/*
* Score class is used when there is a request to show
* leaderboard, it provides the player's name and score.
 */
public class Score {
    private String name;
    private long score;

    Score() {}

    public Score(String name, long score) {
        this.name = name;
        this.score = score;
    }

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
