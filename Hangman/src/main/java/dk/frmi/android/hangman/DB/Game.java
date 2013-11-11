package dk.frmi.android.hangman.DB;

import android.app.Application;
import android.content.Context;

import dk.frmi.android.hangman.Helper;
import dk.frmi.android.hangman.R;

public class Game {
    private long id;
    private String opponent;
    private long highestScore;
    private long score;
    private long streak;
    private int attemptsUsed;
    private int language;
    private int difficulty;
    private String result;
    private String guess;
    private String category;
    private String usedChars;

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


    public int getAttemptsUsed() {
        return attemptsUsed;
    }

    public int getLanguage() {
        return language;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getResult() {
        return result;
    }

    public String getGuess() {
        return guess;
    }
    public String getCategory() {
        return category;
    }

    public String getUsedChars(){
        return usedChars;
    }

    public void setGame(String opponent, long highestScore, long score, long streak,
                        int attemptsUsed, int language, int difficulty, String result,
                        String guess, String category, String usedChars) {
        this.opponent = opponent;
        this.highestScore = highestScore;
        this.score = score;
        this.streak = streak;
        this.attemptsUsed = attemptsUsed;
        this.language = language;
        this.difficulty = difficulty;
        this.result = result;
        this.guess = guess;
        this.category = category;
        this.usedChars = usedChars;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        Helper helper = Helper.getInstance();
        Context context = helper.getContext();
        String strScore = context.getString(R.string.Points);
        String strStreak = context.getString(R.string.Streak);
        String strAttempts = context.getString(R.string.AttemptsLeft);
        return String.format(strScore + ": %s - " + strStreak + ": %s - "+ strAttempts + ": %s/5", String.valueOf(score), String.valueOf(streak), String.valueOf(attemptsUsed));
    }
}
