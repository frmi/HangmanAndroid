package dk.frmi.android.hangman.DB;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class GamesDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_OPPONENT, MySQLiteHelper.COLUMN_HIGHEST, MySQLiteHelper.COLUMN_SCORE,
            MySQLiteHelper.COLUMN_STREAK, MySQLiteHelper.COLUMN_ATTEMPTS, MySQLiteHelper.COLUMN_LANGUAGE,
            MySQLiteHelper.COLUMN_DIFFICULTY, MySQLiteHelper.COLUMN_RESULT, MySQLiteHelper.COLUMN_GUESS,
            MySQLiteHelper.COLUMN_CATEGORY, MySQLiteHelper.COLUMN_USEDCHARS};

    public GamesDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Game createGame(String opponent, long highestScore, long score, long streak,
                           long attemptsUsed, long language, long difficulty, String result,
                           String guess, String category, String usedChars) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_OPPONENT, opponent);
        values.put(MySQLiteHelper.COLUMN_HIGHEST, highestScore);
        values.put(MySQLiteHelper.COLUMN_SCORE, score);
        values.put(MySQLiteHelper.COLUMN_STREAK, streak);
        values.put(MySQLiteHelper.COLUMN_ATTEMPTS, attemptsUsed);
        values.put(MySQLiteHelper.COLUMN_LANGUAGE, language);
        values.put(MySQLiteHelper.COLUMN_DIFFICULTY, difficulty);
        values.put(MySQLiteHelper.COLUMN_RESULT, result);
        values.put(MySQLiteHelper.COLUMN_GUESS, guess);
        values.put(MySQLiteHelper.COLUMN_CATEGORY, category);
        values.put(MySQLiteHelper.COLUMN_USEDCHARS, usedChars);
        long insertId = database.insert(MySQLiteHelper.TABLE_GAMES, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_GAMES,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Game newGame = cursorToGame(cursor);
        cursor.close();
        return newGame;
    }

    public Game getGame(long id){
        Cursor cursor = database.query(MySQLiteHelper.TABLE_GAMES, allColumns,
                MySQLiteHelper.COLUMN_ID + "=" + id, null, null, null, null);

        cursor.moveToFirst();
        Game game = cursorToGame(cursor);
        cursor.close();
        return game;
    }

    public void deleteGame(Game game) {
        long id = game.getId();
        System.out.println("Game deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_GAMES, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public void updateGame(Game game){
        long id = game.getId();
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_OPPONENT, game.getOpponent());
        values.put(MySQLiteHelper.COLUMN_HIGHEST, game.getHighestScore());
        values.put(MySQLiteHelper.COLUMN_SCORE, game.getScore());
        values.put(MySQLiteHelper.COLUMN_STREAK, game.getStreak());
        values.put(MySQLiteHelper.COLUMN_ATTEMPTS, game.getAttemptsUsed());
        values.put(MySQLiteHelper.COLUMN_LANGUAGE, game.getLanguage());
        values.put(MySQLiteHelper.COLUMN_DIFFICULTY, game.getDifficulty());
        values.put(MySQLiteHelper.COLUMN_RESULT, game.getResult());
        values.put(MySQLiteHelper.COLUMN_GUESS, game.getGuess());
        values.put(MySQLiteHelper.COLUMN_CATEGORY, game.getCategory());
        values.put(MySQLiteHelper.COLUMN_USEDCHARS, game.getUsedChars());

        System.out.print("Game updated with id: " + id);
        database.update(MySQLiteHelper.TABLE_GAMES, values, MySQLiteHelper.COLUMN_ID + "=" + id, null);
    }

    public List<Game> getAllGames() {
        List<Game> games = new ArrayList<Game>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_GAMES,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Game game = cursorToGame(cursor);
            games.add(game);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return games;
    }

    private Game cursorToGame(Cursor cursor) {
        Game game = new Game();
        game.setId(cursor.getLong(0));
        game.setGame(cursor.getString(1), cursor.getLong(2), cursor.getLong(3), cursor.getLong(4),
                cursor.getInt(5), cursor.getInt(6), cursor.getInt(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11));
        return game;
    }
}
