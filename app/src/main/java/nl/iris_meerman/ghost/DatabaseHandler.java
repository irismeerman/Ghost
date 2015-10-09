package nl.iris_meerman.ghost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.ResultSet;
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

    // class variables
    String scoreRetrieved;

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
        String query = "SELECT " + KEY_SCORE + " FROM " + TABLE_SCORE + " WHERE " +  KEY_NAME + "= '" + name + "' ";
        Log.d("test query: ", query);
        Cursor cursor = db.rawQuery(query,null );

        if (cursor.moveToFirst()) {
            scoreRetrieved = cursor.getString( cursor.getColumnIndex(KEY_SCORE));
            Log.d("test old score: ", String.valueOf(scoreRetrieved));
        }
        // add 1
        int newScore = Integer.parseInt(scoreRetrieved)  + 1;
        Log.d("test new score: ", String.valueOf(newScore));
        values.put(KEY_SCORE, newScore);
        // put newScore in Key_score where keyname = name
        String newQuery = KEY_NAME + "='"+name+"'";
        db.update(TABLE_SCORE, values, newQuery, null);

        db.close();
        cursor.close();
    }

    // Get all players returns an arraylist of strings of playernames
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

    // this function adds a new playername with score=0 to the database
    public void addPlayer(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name);
        values.put(KEY_SCORE, 0);
        db.insert(TABLE_SCORE, null, values);
        db.close();
    }

    public ArrayList<String> sortHighscores(){
        SQLiteDatabase db = this.getWritableDatabase();
        String sortQuery = "SELECT * FROM " + TABLE_SCORE + " ORDER BY " + KEY_SCORE + " DESC";
        Cursor cursorSorted = db.rawQuery(sortQuery, null);
        //Log.d("test cursorsorted: ", String.valueOf(cursorSorted));
        ArrayList<String> list = new ArrayList<String>();

        if (cursorSorted.moveToFirst()){
            while (!cursorSorted.isAfterLast()) {
                String cursorName = cursorSorted.getString(cursorSorted.getColumnIndex(KEY_NAME));
                String cursorScore = cursorSorted.getString(cursorSorted.getColumnIndex(KEY_SCORE));
                String wholeEntry = cursorName + " " + cursorScore;
                list.add(wholeEntry);
                cursorSorted.moveToNext();
            }
        }
        //String[] asColumnsToReturn = new String[] {KEY_ID_SCORE, KEY_NAME, KEY_SCORE};
        return list;
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
