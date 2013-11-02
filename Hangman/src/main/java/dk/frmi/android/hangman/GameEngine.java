package dk.frmi.android.hangman;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frederik on 01-11-13.
 */
public class GameEngine {
    private final int MAX_ATTEMPS = 5;

    public char[] wordToGuess = null;
    public char[] guessArray = null;
    private List<Character> usedChars = null;
    public int attemptsUsed;

    public GameEngine(){
        /* Initialize variables */
        wordToGuess = getWord().toCharArray();
        guessArray = convertWordToUnderscores(wordToGuess);
        usedChars = new ArrayList<Character>();
        attemptsUsed = 0;
    }

    public String getUsedCharsToDisplay(char ch){
        usedChars.add(ch);
        String temp = "";
        int j = 0;
        for (; j < usedChars.size() - 1; j++){
            temp += usedChars.get(j) + ", ";
        }
        temp += usedChars.get(j);
        return temp;
    }

    public List<Integer> findIndexOfChar(char ch){
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < wordToGuess.length; i++){
            if (Character.toLowerCase(wordToGuess[i]) == ch){
                result.add(i);
            }
        }

        if (result.size() == 0){
            attemptsUsed++;
        }

        return result;
    }

    public boolean isGameWon(){
        String facit = Helper.charArrayToString(wordToGuess).toLowerCase();
        String guess = Helper.charArrayToString(guessArray).toLowerCase();

        return facit.equals(guess);
    }

    public boolean isGameOver(){
        if (attemptsUsed >= MAX_ATTEMPS){
            return true;
        } else {
            return false;
        }
    }

    private String getWord(){
        return "Hangman";
    }

    private char[] convertWordToUnderscores(char[] wordToConvert){
        char[] result = new char[wordToConvert.length];
        for (int i = 0; i < wordToConvert.length; i++){
            if (wordToConvert[i] != ' '){
                result[i] = '_';
            } else {
                result[i] = ' ';
            }
        }
        return result;
    }
}
