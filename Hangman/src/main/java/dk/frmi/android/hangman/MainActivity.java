package dk.frmi.android.hangman;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity {

    Button checkBtn = null;
    EditText guessInput = null;
    TextView wordText = null;
    TextView usedCharsText = null;
    GameEngine game = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeElements();
    }

    private void initializeElements(){
        /* Game Engine */
        game = new GameEngine();

        /* Initialize widgets */
        checkBtn = (Button) findViewById(R.id.btnCheck);
        guessInput = (EditText) findViewById(R.id.guessInput);
        usedCharsText = (TextView) findViewById(R.id.usedCharsView);
        wordText = (TextView) findViewById(R.id.wordText);
        wordText.setText(Helper.charArrayToString(game.guessArray));

        /* Needs to be done after initializing all widgets, to avoid null pointer refs */
        addOnClickListeners();
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
        } else if (game.isGameOver()){
            Toast.makeText(this, R.string.GameOver, Toast.LENGTH_LONG).show();
        } else if (guess.length() == 1){
            char ch = Character.toLowerCase(guess.toCharArray()[0]);
            List<Integer> results = game.findIndexOfChar(ch);
            if (results.size() == 0){
                String usedCharsToDisplay = game.getUsedCharsToDisplay(ch);
                updateTextView(usedCharsText, usedCharsToDisplay);
            } else {
                for (Integer i : results){
                    game.guessArray[i] = game.wordToGuess[i];
                }
                wordText.setText(Helper.charArrayToString(game.guessArray));
            }
            guessInput.setText("");
            if (game.isGameWon()){
                Toast.makeText(this, R.string.Win, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void updateTextView(TextView textView, String string){
        textView.setText(string);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


}
