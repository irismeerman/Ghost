package nl.iris_meerman.ghost;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by iris on 7-10-15.
 */
public class Highscores extends AppCompatActivity {
    String winner, finalword;
    int newScore;
    SharedPreferences highscoresprefs;
    protected ListView highscoreslist;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.highscores);

        Bundle extras = getIntent().getExtras();
        winner = extras.getString("winnername");
        finalword = extras.getString("finalword");

        setWinnerMessage(winner, finalword);
        addPointsToWinner(winner);
        updateDBHighscoreList();
    }

    public void setWinnerMessage(String winner, String finalword){
        TextView message = (TextView) findViewById(R.id.winner_message);
        message.setText("Well done!\n" + winner + " has won the game!\nThe final word was: " + finalword);
    }

    public void addPointsToWinner(String winner){
        highscoresprefs = getSharedPreferences("highscores", Context.MODE_PRIVATE);
        // als de winnaar al bestaat: doe zijn value + 1, anders is value gewoon 1
        if (highscoresprefs.contains(winner)){
            highscoresprefs.getString(winner, "fout");
            newScore = Integer.parseInt("fout") + 1;
        } else {
            newScore = 1;
        }
        SharedPreferences.Editor editor = highscoresprefs.edit();
        editor.putString(winner, String.valueOf(newScore));
        editor.commit();
    }

    public void updateDBHighscoreList(){
        highscoreslist = (ListView) findViewById(R.id.highscoreslist);
        // http://stackoverflow.com/questions/24358091/sqlite-or-sharedpreferences-for-high-scores
        db = new DatabaseHandler(this);
        db.addScore(winner);
    }
}
