package dk.frmi.android.hangman;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Frederik on 01-11-13.
 */
public class GameEngine {

    private GameStatus gameStatus;
    private ArrayList<Word> dictionary;
    private String[] resultArray = null;
    private String[] guessArray = null;
    private String category = null;
    private Context context;

    public GameEngine(Context ctx, InputStream words){
        /* Initialize variables */
        context = ctx;
        gameStatus = new GameStatus();
        dictionary = createDictionary(words);
    }

    public String[] getGuessArray(){
        if (guessArray == null || gameStatus.isGameOver()){
            newWord();
        }
        return guessArray;
    }

    public String[] getResultArray(){
        if (resultArray == null){
            newWord();
        }
        return resultArray;
    }

    public String getCategory(){
        if (category == null){
            newWord();
        }
        return category;
    }

    public void newWord(){
        gameStatus.newRound();
        Word word = getWord();
        resultArray = Helper.stringToArray(word.word);
        category = word.category;
        guessArray = convertWordToUnderscores(resultArray);
    }

    private ArrayList<Word> createDictionary(InputStream inputStream){
        ArrayList<Word> words = new ArrayList<Word>();
        BufferedReader dict = null;
        try {
            dict = new BufferedReader(new InputStreamReader(inputStream));

            String word;
            String category = null;
            while((word = dict.readLine()) != null){
                // if word contains "%" then skip
                if (word.contains("%"))
                    continue;


                if (word.contains("#")){
                    category = word.replace("#", "").trim();
                    continue;
                }

                if (category == null)
                    category = context.getString(R.string.CategoryNotFound);

                word = word.trim();
                words.add(new Word(word, category));
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
        return words;
    }

    public List<Integer> findIndexOfChar(String ch){
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < resultArray.length; i++){
            if (resultArray[i].toLowerCase().equals(ch.toLowerCase())){
                result.add(i);
            }
        }

        if (result.size() == 0){
            gameStatus.incAttempts();
        }

        return result;
    }

    public int getImage(){
        int result = 0;
        switch (gameStatus.getAttemptsUsed()){
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

    public GameStatus getStatus(){
        return gameStatus;
    }

    public boolean isGameWon(){
        boolean result = true;

        for (int i = 0; i < resultArray.length; i++){
            if (resultArray[i].equals(guessArray[i]) == false){
                result = false;
                break;
            }
        }

        if (result == true){
            gameStatus.setWon(true);
            gameStatus.addPoints(resultArray);
        }

        return result;
    }

    public boolean isGameOver(){
        if (gameStatus.isAllAttempsUsed()){
            gameStatus.setLost(true);
            gameStatus.subPoints(resultArray);
            return true;
        } else {
            return false;
        }
    }

    private Word getWord(){
        Word word;
        if (dictionary.size() > 0){
            word = dictionary.get((int)(Math.random() * dictionary.size()));
        } else{
            word = new Word("Hangman", context.getString(R.string.CategoryNotFound));
        }
        return word;
    }

    private String[] convertWordToUnderscores(String[] wordToConvert){
        String[] result = new String[wordToConvert.length];
        for (int i = 0; i < wordToConvert.length; i++){
            if (wordToConvert[i].equals(" ")) {
                result[i] = " ";
            } else if (wordToConvert[i].equals("-")) {
                result[i] = "-";
            } else {
                result[i] = "_";
            }
        }
        return result;
    }
}
