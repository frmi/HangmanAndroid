package dk.frmi.android.hangman;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import dk.frmi.android.hangman.DB.Game;

/**
 * Created by Frederik on 01-11-13.
 */
public class GameMechanics {
    private GameInfo _gameInfo;
    private ArrayList<Word> _dictionary;
    private Context _context;
    private int _difficulty;
    private int _language;

    public GameMechanics(Context context, int language, int difficulty){
        /* Initialize variables */
        _context = context;
        _gameInfo = new GameInfo();
        _difficulty = difficulty;
        _language = language;
        InputStream inputStream = resolveLanguage(_language);
        _dictionary = createDictionary(inputStream);
        _gameInfo.setWord(getWord());
    }

    public GameMechanics(Context context, Game game){
        _context = context;
        Word word = new Word(game.getResult(), game.getCategory(), game.getGuess());
        _gameInfo = new GameInfo(game.getScore(), game.getStreak(), game.getAttemptsUsed(), game.getHighestScore(), word);
        _difficulty = game.getDifficulty();
        _language = game.getLanguage();
        InputStream inputStream = resolveLanguage(_language);
        _dictionary = createDictionary(inputStream);
    }

    public String[] getGuessArray(){
        if (_gameInfo.getWord().guess == null || _gameInfo.isGameOver()){
            newWord();
        }
        return _gameInfo.getWord().guess;
    }

    public String[] getResultArray(){
        if (_gameInfo.getWord().facit == null){
            newWord();
        }
        return _gameInfo.getWord().facit;
    }

    public String getCategory(){
        if (_gameInfo.getWord().category == null){
            newWord();
        }
        return _gameInfo.getWord().category;
    }

    public void newWord(){
        boolean shouldSubtractPoints = _gameInfo.newRound();
        if (shouldSubtractPoints){
            _gameInfo.subPoints(_gameInfo.getWord().facit);
        }
        _gameInfo.setWord(getWord());
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
        String[] facit = _gameInfo.getWord().facit;
        for (int i = 0; i < facit.length; i++){
            if (facit[i].toLowerCase().equals(ch.toLowerCase())){
                result.add(i);
            }
        }

        if (result.size() == 0){
            _gameInfo.wrongGuess(ch);
        }

        return result;
    }

    public int getImage(){
        int result = 0;
        switch (_gameInfo.getAttemptsUsed()){
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

    public GameInfo getStatus(){
        return _gameInfo;
    }

    public boolean isGameWon(){
        boolean result = true;

        String[] facit = _gameInfo.getWord().facit;
        for (int i = 0; i < facit.length; i++){
            if (facit[i].equals(_gameInfo.getWord().guess[i]) == false){
                result = false;
                break;
            }
        }

        if (result == true){
            _gameInfo.setWon(true);
            _gameInfo.addPoints(facit);
        }

        return result;
    }

    public boolean isGameOver(){
        if (_gameInfo.isAllAttempsUsed()){
            _gameInfo.setLost(true);
            _gameInfo.subPoints(_gameInfo.getWord().facit);
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

    public int getDifficulty() {
        return _difficulty;
    }

    public int getLanguage() {
        return _language;
    }
}
