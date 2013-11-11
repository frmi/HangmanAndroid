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
            MySQLiteHelper.COLUMN_OPPONENT, MySQLiteHelper.COLUMN_HIGHEST };

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
                           long attemptsUsed, String result, String guess, String category) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_OPPONENT, opponent);
        values.put(MySQLiteHelper.COLUMN_HIGHEST, highestScore);
        values.put(MySQLiteHelper.COLUMN_SCORE, score);
        values.put(MySQLiteHelper.COLUMN_STREAK, streak);
        values.put(MySQLiteHelper.COLUMN_ATTEMPTS, attemptsUsed);
        values.put(MySQLiteHelper.COLUMN_RESULT, result);
        values.put(MySQLiteHelper.COLUMN_GUESS, guess);
        values.put(MySQLiteHelper.COLUMN_CATEGORY, category);
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
        values.put(MySQLiteHelper.COLUMN_RESULT, game.getResult());
        values.put(MySQLiteHelper.COLUMN_GUESS, game.getGuess());
        values.put(MySQLiteHelper.COLUMN_CATEGORY, game.getCategory());

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
                cursor.getLong(5), cursor.getString(6), cursor.getString(7), cursor.getString(8));
        return game;
    }
}
