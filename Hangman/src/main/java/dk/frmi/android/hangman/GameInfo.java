package dk.frmi.android.hangman;

/**
 * Created by Frederik on 04-11-13.
 */
public class GameInfo {
    private final int MAX_ATTEMPS = 5;

    private int id;
    private long maxScore;
    private long points;
    private long streak;
    private int attemptsUsed;
    private boolean isAllAttempsUsed = false;
    private boolean isWon = false;
    private boolean isLost = false;
    private String opponent = "NONE";
    private String usedChars = "";
    private Word word;

    public GameInfo(){
        points = 0;
        streak = 0;
        attemptsUsed = 0;
    }

    public GameInfo(long points, long streak, int attemptsUsed, long maxScore, Word word){
        this.points = points;
        this.streak = streak;
        this.attemptsUsed = attemptsUsed;
        this.maxScore = maxScore;
        this.word = word;
    }

    public boolean isGameOver(){
        return isWon || isLost;
    }

    public boolean newRound(){
        boolean shouldSubtractPoints = false;
        if (attemptsUsed >= MAX_ATTEMPS || (!isLost && !isWon)) {
            streak = 0;
            shouldSubtractPoints = true;
        }
        attemptsUsed = 0;
        isAllAttempsUsed = false;
        isWon = false;
        isLost = false;
        usedChars = "";
        return shouldSubtractPoints;
    }

    public void wrongGuess(String ch){
        incAttempts();
        usedChars += ch;
    }

    private void incAttempts(){
        attemptsUsed++;
        if (attemptsUsed >= MAX_ATTEMPS){
            isAllAttempsUsed = true;
        }
    }

    public void subPoints(String[] resultArray){
        streak = 0;
        if (points > 0 && resultArray != null){
            this.points -= PointCalculator.calculateLostPoints(resultArray);
        }
    }

    public void addPoints(String[] resultArray){
        streak++;
        this.points += PointCalculator.calculateWinPoints(resultArray, attemptsUsed, streak);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWon(boolean isWon) {
        this.isWon = isWon;
    }

    public void setLost(boolean isLost) {
        this.isLost = isLost;
    }

    public boolean isWon() {
        return isWon;
    }

    public boolean isLost() {
        return isLost;
    }

    public String getMaxScore(){
        if (points > maxScore){
            maxScore = points;
        }
        return String.valueOf(maxScore);
    }

    public String getPoints(){
        return String.valueOf(points);
    }

    public String getStreak() {
        return String.valueOf(streak);
    }

    public int getAttemptsUsed() {
        return attemptsUsed;
    }

    public boolean isAllAttempsUsed() {
        return isAllAttempsUsed;
    }


    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public String getOpponent() {
        return opponent;
    }

    public void setOpponent(String opponent) {
        this.opponent = opponent;
    }

    public String getUsedChars(){
        return usedChars;
    }
}
