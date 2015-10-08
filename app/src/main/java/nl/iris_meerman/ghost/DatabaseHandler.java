package nl.iris_meerman.ghost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.jar.Attributes;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "gameDB";

    // Table name
    private static final String TABLE_SCORE = "highscores";

    // Score Table Columns names
    private static final String KEY_ID_SCORE = "_id";
    private static final String KEY_NAME = "name";
    private static final String KEY_SCORE = "score_value";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("test: ", "onCreate database");
        String CREATE_SCORE_TABLE = "CREATE TABLE " + TABLE_SCORE + " ("
                + KEY_ID_SCORE + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " VARCHAR, "
                + KEY_SCORE + " INTEGER)";
        Log.d("test query: ", CREATE_SCORE_TABLE);
        db.execSQL(CREATE_SCORE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORE);

        // Create tables again
        onCreate(db);
    }

    // Adding new score to winner
    public void addScore(String name) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // select the right cell: the key_score cell belonging to 'name'
        String query = "SELECT KEY_SCORE FROM TABLE_SCORE WHERE KEY_NAME ='name' ";
        // add 1
        int newScore = Integer.parseInt(query) + 1;
        values.put(KEY_SCORE, newScore);
        // put newScore in Key_score where keyname = name
        db.update(TABLE_SCORE, values, KEY_NAME + " = '" + name, null);
        db.close();

    }

    // get all players returns an arraylist of strings of playernames
    // if a given playername does not exist, you can call addplayer.
    public ArrayList<String> getAllPlayers(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_SCORE;
        Cursor c = db.rawQuery(query, null);
        ArrayList<String> players = new ArrayList<>();
        if (c.moveToFirst()) {
            while (!c.isAfterLast()) {
                String name = c.getString(c.getColumnIndex(KEY_NAME));
                //String player = new Player(name, id, gamesPlayed, gamesWon, livesLost);
                players.add(name);
                c.moveToNext();
            }
        }
        c.close();
        return players;
    }

    public void addPlayer(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_SCORE, (int) '0');
        db.insert(TABLE_SCORE, null, values);
        db.close();
    }

    // Getting All Scores
    public String[] getAllScores() {

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SCORE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        int i = 0;

        String[] data = new String[cursor.getCount()];

        while (cursor.moveToNext()) {

            data[i] = cursor.getString(1);

            i = i++;

        }
        cursor.close();
        db.close();
        // return score array
        return data;
    }
}
