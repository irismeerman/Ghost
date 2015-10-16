package nl.iris_meerman.ghost;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

/* GameOverActivity.java
 * This activity is called when the game has ended. It displays the layout with the final word, winner,
 * list of highscores and a button with which you can start a new game.
 */
public class GameOverActivity extends AppCompatActivity {
    String winner, finalWord;
    protected ListView highscoresList;
    DatabaseHandler db;

    // 'onCreate' makes sure that the layout is loaded and retrieves the winner and the
    // word and displayes the highscorelist.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameover);

        Bundle extras = getIntent().getExtras();
        winner = extras.getString("winnername");
        finalWord = extras.getString("finalword");

        setMessage(winner, finalWord);
        updateDBHighscoreList();
        displayHighscores();
    }

    // 'setMessage' displays a nice message for the winner, repeats the word
    // and announces the highscorelist.
    public void setMessage(String winner, String finalword){
        TextView message = (TextView) findViewById(R.id.winner_message);
        message.setText(winner + " " + getResources().getString(R.string.gewonnen) + "\n" + getResources().getString(R.string.woordbericht)
                + " " + finalword.toUpperCase() + "\n\n" + getResources().getString(R.string.highscores)+ ":");
    }

    // 'updateDBHighscoreList' updates the database by adding the score to the winner.
    public void updateDBHighscoreList(){
        db = new DatabaseHandler(this);
        db.addScore(winner);
    }

    // 'displayHighscores' retrieves the whole list of players and their scores from the
    // database, and displays it by using an array adapter.
    public void displayHighscores(){
        highscoresList = (ListView) findViewById(R.id.highscoreslist);
        ArrayList<String> results = db.sortHighscores();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getApplicationContext(),
                android.R.layout.simple_list_item_activated_1, results);
        highscoresList.setAdapter(adapter);
    }

    // 'clickNewGame' makes sure that the user is send to the start activity if he presses
    // the button
    public void clickNewGame(View v){
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
    }

    // 'onBackPressed' makes sure that the back press button on the phone does not respond,
    // this way the user is not able to return to the game, while it was finished.
    public void onBackPressed(){
    }
}
