package nl.iris_meerman.ghost;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by iris on 7-10-15.
 */
public class Highscores extends AppCompatActivity {
    String winner, finalword;
    int newScore;
    SharedPreferences highscoresprefs;
    protected ListView highscoreslist;
    Context context;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscores);

        Bundle extras = getIntent().getExtras();
        winner = extras.getString("winnername");
        finalword = extras.getString("finalword");

        setWinnerMessage(winner, finalword);
        updateDBHighscoreList();
        displayHighscores();
    }

    public void setWinnerMessage(String winner, String finalword){
        TextView message = (TextView) findViewById(R.id.winner_message);
        message.setText(winner + " " + getResources().getString(R.string.gewonnen) + "\n" + getResources().getString(R.string.woordbericht)
                + " " + finalword.toUpperCase() + "\n" + getResources().getString(R.string.highscores));
    }

    public void updateDBHighscoreList(){
        // http://stackoverflow.com/questions/24358091/sqlite-or-sharedpreferences-for-high-scores
        db = new DatabaseHandler(this);
        db.addScore(winner);
    }

    public void displayHighscores(){
        highscoreslist = (ListView) findViewById(R.id.highscoreslist);
        ArrayList<String> results = db.sortHighscores();
        //CursorAdapter cursorAdapter =

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getApplicationContext(),
                android.R.layout.simple_list_item_activated_1, results);

        highscoreslist.setAdapter(adapter);
    }

    public void clickNewGame(View v){
        Intent intent = new Intent(this, start.class);
        startActivity(intent);
    }
}
