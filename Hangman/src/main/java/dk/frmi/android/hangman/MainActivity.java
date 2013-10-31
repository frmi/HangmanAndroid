package dk.frmi.android.hangman;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    Button checkBtn = null;
    EditText guessInput = null;
    TextView wordText = null;
    TextView usedCharsText = null;
    char[] wordToGuess = null;
    char[] guessArray = null;
    List<Character> usedChars = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeElements();
    }

    private void initializeElements(){
        /* Initialize variables */
        wordToGuess = getWord().toCharArray();
        guessArray = convertWordToUnderscores(wordToGuess);
        usedChars = new ArrayList<Character>();

        /* Initialize widgets */
        checkBtn = (Button) findViewById(R.id.btnCheck);
        guessInput = (EditText) findViewById(R.id.guessInput);
        usedCharsText = (TextView) findViewById(R.id.usedCharsView);
        wordText = (TextView) findViewById(R.id.wordText);
        wordText.setText(charArrayToString(guessArray));

        /* Needs to be done after initializing all widgets, to avoid null pointer refs */
        addOnClickListeners();
    }

    private String charArrayToString(char[] array){
        return new String(array);
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

    private void addOnClickListeners(){
        /* add listener for check button */
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess();
            }
        });
    }

    private void guess(){
        String guess = guessInput.getText().toString();
        /* Check whether guess is empty, if empty short circuit with message */
        if (guess.equalsIgnoreCase("") || guess.equals(" ")){
            Toast.makeText(this, R.string.ErrNoInput, Toast.LENGTH_SHORT).show();
            return;
        }
        if (guess.length() == 1){
            char ch = guess.toCharArray()[0];
            List<Integer> results = findIndexOfChar(ch);
            if (results.size() == 0){
                usedChars.add(ch);
                //updateTextView(usedCharsText, (char) usedChars.toArray());
            }
            for (Integer i : results){
                guessArray[i] = ch;
            }
        }
    }

    private void updateTextView(TextView textView, char[] array){

    }

    private List<Integer> findIndexOfChar(char ch){
        List<Integer> result = new ArrayList<Integer>();
        for (int i = 0; i < wordToGuess.length; i++){
            if (wordToGuess[i] == ch){
                result.add(i);
            }
        }
        return result;
    }

    private String getWord(){
        return "Hangman";
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}
