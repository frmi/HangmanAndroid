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
    private GameStatus _gameStatus;
    private ArrayList<Word> _dictionary;
    private Word _word;
    private Context _context;
    private int _difficulty;

    public GameEngine(Context context, int language, int difficulty){
        /* Initialize variables */
        _context = context;
        _gameStatus = new GameStatus();
        _difficulty = difficulty;
        InputStream inputStream = resolveLanguage(language);
        _dictionary = createDictionary(inputStream);
        _word = getWord();
    }

    public String[] getGuessArray(){
        if (_word.guess == null || _gameStatus.isGameOver()){
            newWord();
        }
        return _word.guess;
    }

    public String[] getResultArray(){
        if (_word.facit == null){
            newWord();
        }
        return _word.facit;
    }

    public String getCategory(){
        if (_word.category == null){
            newWord();
        }
        return _word.category;
    }

    public void newWord(){
        boolean shouldSubtractPoints = _gameStatus.newRound();
        if (shouldSubtractPoints){
            _gameStatus.subPoints(_word.facit);
        }
        _word = getWord();
    }

    private InputStream resolveLanguage(int languageId){
        int result;

        switch (languageId){
            case 0:
                result = R.raw.uk_cat;
                break;
            case 1:
                result = R.raw.dk_cat;
                break;
            default:
                result = R.raw.uk_cat;
        }
        return  _context.getResources().openRawResource(result);
    }

    private boolean doesWordMatchDifficulty(String word){
        boolean result = false;
        if (_difficulty == Difficulty.Easy.ordinal() && word.length() <= 4){
            result = true;
        } else if (_difficulty == Difficulty.Normal.ordinal() && word.length() <= 6) {
            result = true;
        } else if (_difficulty == Difficulty.Hard.ordinal() && word.length() > 4){
            result = true;
        }

        return result;
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
                    category = _context.getString(R.string.CategoryNotFound);

                if (doesWordMatchDifficulty(word)){
                    word = word.trim();
                    words.add(new Word(word, category));
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
        return words;
    }

    public List<Integer> findIndexOfChar(String ch){
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < _word.facit.length; i++){
            if (_word.facit[i].toLowerCase().equals(ch.toLowerCase())){
                result.add(i);
            }
        }

        if (result.size() == 0){
            _gameStatus.incAttempts();
        }

        return result;
    }

    public int getImage(){
        int result = 0;
        switch (_gameStatus.getAttemptsUsed()){
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
        return _gameStatus;
    }

    public boolean isGameWon(){
        boolean result = true;

        for (int i = 0; i < _word.facit.length; i++){
            if (_word.facit[i].equals(_word.guess[i]) == false){
                result = false;
                break;
            }
        }

        if (result == true){
            _gameStatus.setWon(true);
            _gameStatus.addPoints(_word.facit);
        }

        return result;
    }

    public boolean isGameOver(){
        if (_gameStatus.isAllAttempsUsed()){
            _gameStatus.setLost(true);
            _gameStatus.subPoints(_word.facit);
            return true;
        } else {
            return false;
        }
    }

    private Word getWord(){
        Word word;
        if (_dictionary.size() > 0){
            word = _dictionary.get((int)(Math.random() * _dictionary.size()));
        } else{
            word = new Word("Hangman", _context.getString(R.string.CategoryNotFound));
        }
        //word = new Word("hold fast-mand", "test"); // USED FOR TESTING
        return word;
    }
}
