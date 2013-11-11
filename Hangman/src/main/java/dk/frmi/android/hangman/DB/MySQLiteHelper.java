package dk.frmi.android.hangman.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_GAMES          = "games";
    public static final String COLUMN_ID            = "_id";
    public static final String COLUMN_HIGHEST       = "highest";
    public static final String COLUMN_STREAK        = "streak";
    public static final String COLUMN_SCORE         = "score";
    public static final String COLUMN_OPPONENT      = "opponent";
    public static final String COLUMN_ATTEMPTS      = "attempts";
    public static final String COLUMN_LANGUAGE      = "language";
    public static final String COLUMN_DIFFICULTY    = "difficulty";
    public static final String COLUMN_RESULT        = "result";
    public static final String COLUMN_GUESS         = "guess";
    public static final String COLUMN_CATEGORY      = "category";
    public static final String COLUMN_USEDCHARS      = "usedchars";

    private static final String DATABASE_NAME = "hangman.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "CREATE TABLE "
            + TABLE_GAMES         + "("
            + COLUMN_ID           + " integer primary key autoincrement, "
            + COLUMN_OPPONENT     + " text not null, "
            + COLUMN_HIGHEST      + " integer not null, "
            + COLUMN_SCORE        + " integer not null, "
            + COLUMN_STREAK       + " integer not null, "
            + COLUMN_ATTEMPTS     + " integer not null, "
            + COLUMN_LANGUAGE     + " integer not null, "
            + COLUMN_DIFFICULTY   + " integer not null, "
            + COLUMN_RESULT       + " text not null, "
            + COLUMN_GUESS        + " text not null, "
            + COLUMN_CATEGORY     + " text not null, "
            + COLUMN_USEDCHARS     + " text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAMES);
        onCreate(db);
    }

}