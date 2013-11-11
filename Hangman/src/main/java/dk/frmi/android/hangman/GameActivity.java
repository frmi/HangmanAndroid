package dk.frmi.android.hangman;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import dk.frmi.android.hangman.DB.Game;
import dk.frmi.android.hangman.DB.GamesDataSource;

public class GameActivity extends Activity {

    Button qBtn, wBtn, eBtn, rBtn, tBtn, yBtn, uBtn, iBtn, oBtn, pBtn, aaBtn, aBtn, sBtn, dBtn, fBtn, gBtn, hBtn, jBtn, kBtn, lBtn, aeBtn, oeBtn, zBtn, xBtn, cBtn, vBtn, bBtn, nBtn, mBtn;
    Map<String, Button> keyboardButtons;
    Button resetBtn;
    LetterSpacingTextView wordText = null;
    TextView maxPointsView = null;
    TextView pointsView = null;
    TextView streakView = null;
    TextView categoryView = null;
    ImageView imageView = null;
    GameMechanics gameMechanics = null;
    GamesDataSource datasource;
    Game game = null;
    boolean wrongGuess = false;
    boolean isNewGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        datasource = new GamesDataSource(this);
        datasource.open();
        initializeElements();
    }

    private void initializeElements(){
        /* initialize variables */
        keyboardButtons = new HashMap<String, Button>();

        /* Initialize widgets */
        initKeyboardButtons();
        resetBtn = (Button) findViewById(R.id.newWordBtn);
        wordText = new LetterSpacingTextView(this);
        wordText.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        wordText.setLetterSpacing(LetterSpacingTextView.LetterSpacing.NORMAL);
        //Add the textView in a layout, for instance:
        ((FrameLayout) findViewById(R.id.wordLayout)).addView(wordText);
        pointsView = (TextView) findViewById(R.id.score);
        streakView = (TextView) findViewById(R.id.streak);
        maxPointsView = (TextView) findViewById(R.id.maxPointsView);
        categoryView = (TextView) findViewById(R.id.categoryView);
        imageView = (ImageView) findViewById(R.id.imageView);

        /* Game */
        isNewGame = getIntent().getBooleanExtra("NEWGAME", false);
        if (isNewGame){
            /* Game Engine */
            game = new Game();
            int difficulty = getIntent().getIntExtra("DIFFICULTY", 0);
            int language = getIntent().getIntExtra("LANGUAGE", 0);
            gameMechanics = new GameMechanics(this, language, difficulty);
            resetWordAndImage();
        } else {
            long id = getIntent().getLongExtra("GAME", 0);
            game = datasource.getGame(id);
            gameMechanics = new GameMechanics(this, game);
            initUsedButtons(game.getUsedChars());
            setWordAndImage();
        }

        /* add listener for reset button */
        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (gameMechanics.getStatus().isWon()) {
                    resetWordAndImage();
                } else {
                    if ((gameMechanics.getStatus().isLost())) {
                        resetWordAndImage();
                    } else {
                        showDialogOkCancel(getString(R.string.Warning), getString(R.string.WarningMessage), new Callable<Void>() {
                            @Override
                            public Void call() throws Exception {
                                return resetWordAndImage();
                            }
                        }, null);
                    }
                }
            }
        });
    }

    private void initUsedButtons(String usedChars){
        String[] chars = Helper.stringToArray(usedChars);

        for(int i = 0; i < chars.length; i++){
            Button btn = keyboardButtons.get(chars[i]);
            btn.setEnabled(false);
            btn.setTextColor(Color.RED);
        }
        String[] guessArray = gameMechanics.getGuessArray();
        for(int i = 0; i < guessArray.length; i++){
            Button btn = keyboardButtons.get(guessArray[i]);

            if (btn != null){
                btn.setEnabled(false);
                btn.setTextColor(Color.GREEN);
            }
        }
    }

    private void setWordAndImage(){
        updateWordAndCategoryView(gameMechanics.getGuessArray());
        updateScoreboard();
        ImageBackgroundTask task = new ImageBackgroundTask(imageView);
        task.execute(gameMechanics.getImage());
    }

    private Void resetWordAndImage(){
        gameMechanics.newWord();
        updateWordAndCategoryView(gameMechanics.getGuessArray());
        updateScoreboard();
        ImageBackgroundTask task = new ImageBackgroundTask(imageView);
        task.execute(R.drawable.hangman);
        changeButtonState(true, Color.BLACK);
        return null;
    }

    private void deactivateBtn(Button btn){
        int color;
        if (wrongGuess)
            color = Color.RED;
        else
            color = Color.GREEN;

        btn.setTextColor(color);
        btn.setEnabled(false);
    }

    private void initKeyboardButtons(){
        qBtn = (Button) findViewById(R.id.qBtn);
        wBtn = (Button) findViewById(R.id.wBtn);
        eBtn = (Button) findViewById(R.id.eBtn);
        rBtn = (Button) findViewById(R.id.rBtn);
        tBtn = (Button) findViewById(R.id.tBtn);
        yBtn = (Button) findViewById(R.id.yBtn);
        uBtn = (Button) findViewById(R.id.uBtn);
        iBtn = (Button) findViewById(R.id.iBtn);
        oBtn = (Button) findViewById(R.id.oBtn);
        pBtn = (Button) findViewById(R.id.pBtn);
        aaBtn = (Button) findViewById(R.id.aaBtn);
        aBtn = (Button) findViewById(R.id.aBtn);
        sBtn = (Button) findViewById(R.id.sBtn);
        dBtn = (Button) findViewById(R.id.dBtn);
        fBtn = (Button) findViewById(R.id.fBtn);
        gBtn = (Button) findViewById(R.id.gBtn);
        hBtn = (Button) findViewById(R.id.hBtn);
        jBtn = (Button) findViewById(R.id.jBtn);
        kBtn = (Button) findViewById(R.id.kBtn);
        lBtn = (Button) findViewById(R.id.lBtn);
        aeBtn = (Button) findViewById(R.id.aeBtn);
        oeBtn = (Button) findViewById(R.id.oeBtn);
        zBtn = (Button) findViewById(R.id.zBtn);
        xBtn = (Button) findViewById(R.id.xBtn);
        cBtn = (Button) findViewById(R.id.cBtn);
        vBtn = (Button) findViewById(R.id.vBtn);
        bBtn = (Button) findViewById(R.id.bBtn);
        nBtn = (Button) findViewById(R.id.nBtn);
        mBtn = (Button) findViewById(R.id.mBtn);
        keyboardButtons.put("q", qBtn);
        keyboardButtons.put("w", wBtn);
        keyboardButtons.put("e", eBtn);
        keyboardButtons.put("r", rBtn);
        keyboardButtons.put("t", tBtn);
        keyboardButtons.put("y", yBtn);
        keyboardButtons.put("u", uBtn);
        keyboardButtons.put("i", iBtn);
        keyboardButtons.put("o", oBtn);
        keyboardButtons.put("p", pBtn);
        keyboardButtons.put("aa", aaBtn);
        keyboardButtons.put("a", aBtn);
        keyboardButtons.put("s", sBtn);
        keyboardButtons.put("d", dBtn);
        keyboardButtons.put("f", fBtn);
        keyboardButtons.put("g", gBtn);
        keyboardButtons.put("h", hBtn);
        keyboardButtons.put("j", jBtn);
        keyboardButtons.put("k", kBtn);
        keyboardButtons.put("l", lBtn);
        keyboardButtons.put("ae", aeBtn);
        keyboardButtons.put("oe", oeBtn);
        keyboardButtons.put("z", zBtn);
        keyboardButtons.put("x", xBtn);
        keyboardButtons.put("c", cBtn);
        keyboardButtons.put("v", vBtn);
        keyboardButtons.put("b", bBtn);
        keyboardButtons.put("n", nBtn);
        keyboardButtons.put("m", mBtn);
        addOnClickListenersForKeyboardButtons();
    }

    private void addOnClickListenersForKeyboardButtons(){

        /* Add listeners for keyboard buttons */
        qBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("q");
                deactivateBtn(qBtn);
            }
        });
        wBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 guess("w");
                 deactivateBtn(wBtn);
             }
         });
        eBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("e");
                deactivateBtn(eBtn);
            }
        });
        rBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("r");
                deactivateBtn(rBtn);
            }
        });
        tBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("t");
                deactivateBtn(tBtn);
            }
        });
        yBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("y");
                deactivateBtn(yBtn);
            }
        });
        uBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("u");
                deactivateBtn(uBtn);

            }
        });
        iBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("i");
                deactivateBtn(iBtn);
            }
        });
        oBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("o");
                deactivateBtn(oBtn);
            }
        });
        pBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("p");
                deactivateBtn(pBtn);
            }
        });
        aaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("å");
                deactivateBtn(aaBtn);
            }
        });
        aBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("a");
                deactivateBtn(aBtn);
            }
        });
        sBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("s");
                deactivateBtn(sBtn);
            }
        });
        dBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("d");
                deactivateBtn(dBtn);
            }
        });
        fBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("f");
                deactivateBtn(fBtn);
            }
        });
        gBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("g");
                deactivateBtn(gBtn);
            }
        });
        hBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("h");
                deactivateBtn(hBtn);
            }
        });
        jBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("j");
                deactivateBtn(jBtn);
            }
        });
        kBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("k");
                deactivateBtn(kBtn);
            }
        });
        lBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("l");
                deactivateBtn(lBtn);
            }
        });
        aeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("æ");
                deactivateBtn(aeBtn);
            }
        });
        oeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("ø");
                deactivateBtn(oeBtn);
            }
        });
        zBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("z");
                deactivateBtn(zBtn);
            }
        });
        xBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("x");
                deactivateBtn(xBtn);
            }
        });
        cBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("c");
                deactivateBtn(cBtn);
            }
        });
        vBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("v");
                deactivateBtn(vBtn);
            }
        });
        bBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("b");
                deactivateBtn(bBtn);
            }
        });
        nBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("n");
                deactivateBtn(nBtn);
            }
        });
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guess("m");
                deactivateBtn(mBtn);
            }
        });

    }

    private void guess(String ch){
        String[] guessArray = gameMechanics.getGuessArray();
        String[] resultArray = gameMechanics.getResultArray();
        List<Integer> results = gameMechanics.findIndexOfChar(ch);
        if (results.size() == 0){
            wrongGuess = true;

            ImageBackgroundTask task = new ImageBackgroundTask(imageView);
            task.execute(gameMechanics.getImage());
        } else {
            for (Integer i : results){
                guessArray[i] = resultArray[i];
            }
            updateWordAndCategoryView(guessArray);
            wrongGuess = false;
        }

        checkGameStatus();
    }

    private void checkGameStatus(){
        if (gameMechanics.isGameOver()){
            updateScoreboard();
            updateWordAndCategoryView("Ordet var: " + Helper.stringArrayToString(gameMechanics.getResultArray()));
            Toast.makeText(this, R.string.GameOver, Toast.LENGTH_LONG).show();
            changeButtonState(false, null);
        } else if (gameMechanics.isGameWon()){
            Toast.makeText(this, R.string.Win, Toast.LENGTH_LONG).show();
            updateScoreboard();
            changeButtonState(false, null);
        }
    }

    private void changeButtonState(boolean enabled, Integer color){
        Iterator it = keyboardButtons.entrySet().iterator();

        while (it.hasNext()){
            Map.Entry pairs = (Map.Entry)it.next();
            Button b = (Button) pairs.getValue();
            if (color != null)
                b.setTextColor(color);
            b.setEnabled(enabled);
        }
    }

    private void updateWordAndCategoryView(String[] wordArray){
        wordText.setText(Helper.stringArrayToString(wordArray));
        categoryView.setText(gameMechanics.getCategory());
    }

    private void updateWordAndCategoryView(String word){
        wordText.setText(word);
        categoryView.setText(gameMechanics.getCategory());
    }

    private void updateScoreboard(){
        GameInfo status = gameMechanics.getStatus();
        pointsView.setText(status.getPoints());
        streakView.setText(status.getStreak());
        maxPointsView.setText(status.getMaxScore());
    }

    private void showDialogOkCancel(String title, CharSequence message, final Callable<Void> positiveFunc, final Callable<Void> negativeFunc) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if (title != null)
            builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (positiveFunc != null)
                        positiveFunc.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (negativeFunc != null)
                        negativeFunc.call();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.show();
    }

    private void updateGameInstance(){
        GameInfo gameInfo = gameMechanics.getStatus();
        Word word = gameInfo.getWord();
        String guess = Helper.stringArrayToString(word.guess);
        String result = word.word;
        String category = word.category;
        String opponent = gameInfo.getOpponent();
        String usedChars = gameInfo.getUsedChars();
        long score = Long.valueOf(gameInfo.getPoints());
        int language = gameMechanics.getLanguage();
        int difficulty = gameMechanics.getDifficulty();
        int attemptsUsed = gameInfo.getAttemptsUsed();
        long streak = Long.valueOf(gameInfo.getStreak());
        long highestScore = Long.valueOf(gameInfo.getMaxScore());

        if (isNewGame){
            datasource.createGame(opponent, highestScore, score, streak, attemptsUsed, language, difficulty, result, guess, category, usedChars);
        } else if (!isNewGame && !gameInfo.isWon() && !gameInfo.isLost()){
            game.setGame(opponent, highestScore, score, streak, attemptsUsed, language, difficulty, result, guess, category, usedChars);
            datasource.updateGame(game);
        } else {
            datasource.deleteGame(game);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {
        updateGameInstance();
        datasource.close();
        super.onPause();
    }

    class ImageBackgroundTask extends AsyncTask<Integer, Void, Bitmap> {
        private final WeakReference<ImageView> imageViewReference;
        private int data = 0;

        public ImageBackgroundTask(ImageView imageView) {
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = params[0];
            return Helper.decodeSampledBitmapFromResource(getResources(), data);
        }

        // Once complete, see if ImageView is still around and set bitmap.
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
}

