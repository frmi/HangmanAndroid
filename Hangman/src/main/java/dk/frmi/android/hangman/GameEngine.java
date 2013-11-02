package dk.frmi.android.hangman;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Frederik on 01-11-13.
 */
public class GameEngine {
    private final int MAX_ATTEMPS = 5;

    private ArrayList<String> dictionary;
    public char[] wordToGuess = null;
    public char[] guessArray = null;
    public int attemptsUsed = 0;
    Context context;

    public GameEngine(Context ctx, InputStream words){
        /* Initialize variables */
        context = ctx;
        dictionary = new ArrayList<String>();
        createDictionary(words);
        wordToGuess = getWord().toCharArray();
        guessArray = convertWordToUnderscores(wordToGuess);
    }

    public void reset(){
        wordToGuess = getWord().toCharArray();
        guessArray = convertWordToUnderscores(wordToGuess);
        attemptsUsed = 0;
    }

    private ArrayList<String> createDictionary(InputStream inputStream){

        BufferedReader dict = null;
        try {
            //dictionary.txt should be in the assets folder.
            dict = new BufferedReader(new InputStreamReader(inputStream));

            String word;
            while((word = dict.readLine()) != null){
                if(word.length() >= 3){
                    dictionary.add(word);
                }
            }

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            dict.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return dictionary;
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

    public int getImage(){
        int result = 0;
        switch (attemptsUsed){
            case 0:
                result = R.drawable.hangman1;
                break;
            case 1:
                result = R.drawable.hangman1;
                break;
            case 2:
                result = R.drawable.hangman2;
                break;
            case 3:
                result = R.drawable.hangman3;
                break;
            case 4:
                result = R.drawable.hangman4;
                break;
            case 5:
                result = R.drawable.hangman5;
                break;
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
        String word;
        if (dictionary.size() > 0){
            word = dictionary.get((int)(Math.random() * dictionary.size()));
        } else{
            word = "Hangman";
        }
        return word;
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
