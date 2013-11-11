package dk.frmi.android.hangman;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.Random;

import dk.frmi.android.hangman.DB.GamesDataSource;
import dk.frmi.android.hangman.DB.Game;

/**
 * Created by fm on 11/10/13.
 */
public class ActiveGamesActivity extends Activity {
    private GamesDataSource datasource;
    private ListView gamesList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activegames);

        datasource = new GamesDataSource(this);
        datasource.open();

        List<Game> values = datasource.getAllGames();
        gamesList = (ListView) findViewById(R.id.gamesList);

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Game> adapter = new ArrayAdapter<Game>(this,
                android.R.layout.simple_list_item_1, values);
        gamesList.setAdapter(adapter);

        // set OnClickListener
        gamesList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Game game = (Game) gamesList.getSelectedItem();
                /* TODO: implement open saved game
                 * Change constructors of other classes
                 * Fix tables for GameInfo and GameMechanics.
                 */
            }
        });
    }

    // Will be called via the onClick attribute
    // of the buttons in main.xml
    public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Game> adapter = (ArrayAdapter<Game>) gamesList.getAdapter();
        Game game = null;

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        datasource.close();
        super.onPause();
    }

}