package dk.frmi.android.hangman.DB;

public class Game {
    private long id;
    private String opponent;
    private long highestScore;
    private long score;
    private long streak;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOpponent() {
        return opponent;
    }

    public long getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(long highestScore) {
        this.highestScore = highestScore;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getStreak() {
        return streak;
    }

    public void setStreak(long streak) {
        this.streak = streak;
    }

    public void setGame(String opponent, long highestScore, long score, long streak) {
        this.opponent = opponent;
        this.highestScore = highestScore;
        this.score = score;
        this.streak = streak;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return String.format("%s - Score: %s, Streak: %s", opponent, String.valueOf(score), String.valueOf(streak));
    }
}
