package nl.iris_meerman.ghost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

/* DatabaseHandler.java
 * This DatabaseHandler class communicates with the SQLite database in which the players
 * and their scores are saved. In this class, players can be added, a list of all players
 * can be retrieved, scores can be updated, and all players with their scores can be retrieved.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "gameDB";
    private static final String TABLE_SCORE = "highscores";
    // Score Table Columns names
    private static final String KEY_ID_SCORE = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SCORE = "score_value";

    String oldScore;
    SQLiteDatabase db;
    String cursorName, cursorScore, nameAndScore;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // 'onCreate' creates the table with columns: id, playername and score
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SCORE_TABLE = "CREATE TABLE " + TABLE_SCORE + " ("
                + KEY_ID_SCORE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " VARCHAR, "
                + KEY_SCORE + " INTEGER)";
        db.execSQL(CREATE_SCORE_TABLE);
    }

    // 'addPlayer' adds a given (via input from the user) playername with score=0 to the database
    public void addPlayer(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_SCORE, 0);
        db.insert(TABLE_SCORE, null, values);
        db.close();
    }

    // 'getAllPlayers' returns an arraylist of all playernames in the database
    // (ordered alphabetically). This is used for selecting a previously used
    // playername for the game.
    public ArrayList<String> getAllPlayers(){
        db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_SCORE + " ORDER BY " + KEY_NAME;
        Cursor c = db.rawQuery(query, null);
        ArrayList<String> players = new ArrayList<>();
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                String name = c.getString(c.getColumnIndex(KEY_NAME));
                players.add(name);
                c.moveToNext();
            }
        }
        c.close();
        return players;
    }

    // 'addScore' adds 1 to the score in the database of the given playername (the winner).
    public void addScore(String name) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        // retrieve old score
        String getScoreQuery = "SELECT " + KEY_SCORE + " FROM " + TABLE_SCORE + " WHERE " +  KEY_NAME + "= '" + name + "' ";
        Cursor cursor = db.rawQuery(getScoreQuery,null);
        if (cursor.moveToFirst()) {
            oldScore = cursor.getString( cursor.getColumnIndex(KEY_SCORE));
        }
        // save new score
        int newScore = Integer.parseInt(oldScore)  + 1;
        values.put(KEY_SCORE, newScore);
        String setScoreQuery = KEY_NAME + "='"+name+"'";
        db.update(TABLE_SCORE, values, setScoreQuery, null);

        db.close();
        cursor.close();
    }

    // 'sortHighscores' returns an arraylist of all playernames ever used in the game and their score,
    // sorted based on score. This method is used for displaying the highscore.
    public ArrayList<String> sortHighscores(){
        db = this.getWritableDatabase();
        String sortQuery = "SELECT * FROM " + TABLE_SCORE + " ORDER BY " + KEY_SCORE + " DESC";
        Cursor cursorSorted = db.rawQuery(sortQuery, null);
        ArrayList<String> highscoreList = new ArrayList<>();

        if (cursorSorted.moveToFirst()){
            while (!cursorSorted.isAfterLast()) {
                cursorName = cursorSorted.getString(cursorSorted.getColumnIndex(KEY_NAME));
                cursorScore = cursorSorted.getString(cursorSorted.getColumnIndex(KEY_SCORE));
                nameAndScore = cursorName + " - " + cursorScore;
                highscoreList.add(nameAndScore);
                cursorSorted.moveToNext();
            }
        }
        return highscoreList;
    }

    // 'onUpgrade' is not used, but necessary to let the DatabaseHandler class work.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
