package dk.frmi.android.hangman.DB;

public class Game {
    private long id;
    private String opponent;
    private long highestScore;
    private long score;
    private long streak;
    private long attemptsUsed;
    private String result;
    private String guess;
    private String category;

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

    public long getScore() {
        return score;
    }

    public long getStreak() {
        return streak;
    }

    public String getResult() {
        return result;
    }

    public long getAttemptsUsed() {
        return attemptsUsed;
    }

    public String getGuess() {
        return guess;
    }
    public String getCategory() {
        return category;
    }

    public void setGame(String opponent, long highestScore, long score, long streak,
                        long attemptsUsed, String result, String guess, String category) {
        this.opponent = opponent;
        this.highestScore = highestScore;
        this.score = score;
        this.streak = streak;
        this.attemptsUsed = attemptsUsed;
        this.result = result;
        this.guess = guess;
        this.category = category;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return String.format("%s - Score: %s, Streak: %s", opponent, String.valueOf(score), String.valueOf(streak));
    }
}
