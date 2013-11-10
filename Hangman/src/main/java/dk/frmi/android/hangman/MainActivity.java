package dk.frmi.android.hangman;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Locale;

/**
 * Created by fm on 11/9/13.
 */
public class MainActivity extends Activity {
    private Spinner difficultiesSpinner = null;
    private Spinner languageSpinner = null;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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