package dk.frmi.android.hangman;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.List;
import java.util.Locale;

import dk.frmi.android.hangman.DB.Game;
import dk.frmi.android.hangman.DB.GamesDataSource;

/**
 * Created by fm on 11/9/13.
 */
public class MainActivity extends Activity {
    private Spinner difficultiesSpinner = null;
    private Spinner languageSpinner = null;
    private GamesDataSource datasource;
    private ListView gamesList;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start connection to DB
        datasource = new GamesDataSource(this);
        datasource.open();

        // Load all saved games
        List<Game> values = datasource.getAllGames();
        gamesList = (ListView) findViewById(R.id.gamesList);

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Game> adapter = new ArrayAdapter<Game>(this,
                android.R.layout.simple_list_item_1, values);
        gamesList.setAdapter(adapter);

        // set OnClickListener
        /*gamesList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game game = (Game) gamesList.getSelectedItem();

            }
        });*/

        difficultiesSpinner = (Spinner) findViewById(R.id.difficultySpinner);
        languageSpinner = (Spinner) findViewById(R.id.languageSpinner);
        addAdapterToSpinner(difficultiesSpinner, R.array.difficulties_array);
        addAdapterToSpinner(languageSpinner, R.array.language_array);

        // Set device language as default pick
        if (Locale.getDefault().getDisplayLanguage().equals("dansk")){
            languageSpinner.setSelection(1);
        }

        Button playButton = (Button) findViewById(R.id.playButton);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gameIntent = new Intent(v.getContext(), GameActivity.class);
                String difficultyId = "DIFFICULTY";
                String languageId = "LANGUAGE";

                gameIntent.putExtra(languageId, languageSpinner.getSelectedItemPosition());
                gameIntent.putExtra(difficultyId, difficultiesSpinner.getSelectedItemPosition());
                v.getContext().startActivity(gameIntent);
            }
        });

    }

    private void addAdapterToSpinner(Spinner spinner, int resourceId){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                resourceId, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

}