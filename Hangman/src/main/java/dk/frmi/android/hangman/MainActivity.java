package dk.frmi.android.hangman;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    private List<Game> values;
    private Helper helper;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // init helper class
        helper = Helper.getInstance();
        helper.setContext(this);

        // Start connection to DB
        datasource = new GamesDataSource(this);
        datasource.open();

        //datasource.createGame("SINGLE PLAYER", 1000, 250, 3, 0, 1, 0, "Hangman", "_angman", "Database");

        // Load all saved games
        values = datasource.getAllGames();
        gamesList = (ListView) findViewById(R.id.gamesList);

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Game> adapter = new ArrayAdapter<Game>(this,
                android.R.layout.simple_list_item_1, values);
        gamesList.setAdapter(adapter);
        gamesList.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

        gamesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long index) {
                Intent gameIntent = new Intent(view.getContext(), GameActivity.class);
                String gameId = "GAME";

                long id = values.get((int) index).getId();

                gameIntent.putExtra(gameId, id);
                view.getContext().startActivity(gameIntent);
            }
        });

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
                String newGameId = "NEWGAME";

                gameIntent.putExtra(languageId, languageSpinner.getSelectedItemPosition());
                gameIntent.putExtra(difficultyId, difficultiesSpinner.getSelectedItemPosition());
                gameIntent.putExtra(newGameId, true);
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

    @Override
    protected void onResume() {
        datasource.open();
        values = datasource.getAllGames();
        ArrayAdapter<Game> adapter = new ArrayAdapter<Game>(this,
                android.R.layout.simple_list_item_1, values);
        gamesList.setAdapter(adapter);
        super.onResume();
    }

}