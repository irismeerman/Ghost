package nl.iris_meerman.ghost;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/* GameActivity.java
 * This class is the activity of the game. The regulations are saved in Game, in this
 * class messages are set to the screen and it responds to new letters given by the
 * players. It starts an intent to the GameOverActivity as soon as there is a winner.
 */
public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    Lexicon lexicon;
    SharedPreferences gamePreferences;
    EditText editTextNewLetter;
    String player, inputLetter, letterGuess, wordGuess, winnerPlayer;
    int winnerInt;
    private Game game;
    public static final String sp = "gamePreferences";

    // 'onCreate' calls the layout file to be loaded and reads the lexicon. Based
    // on the selected language, it reads in the Dutch or English one. By default,
    // it selects the English one.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        gamePreferences = getSharedPreferences(sp, Context.MODE_PRIVATE);

        // select correct lexicon for game
        String language = gamePreferences.getString("language", "en");
        if (language == "nl") {
            lexicon = new Lexicon(R.raw.dutch, this);
        } else {
            lexicon = new Lexicon(R.raw.english, this);
        }
        game = new Game(lexicon);
        startGame();
    }

    // 'startGame' start the game by telling the user who's turn it is. The rest
    // of the game is activitates by pushing the 'submit' button.
    public void startGame() {
        setTurnMessage();
    }

    // 'submitLetterButton' gets the given letter and updates the guessed word so far.
    // Based on whether the game has 'ended', it decides to display the new word and who's
    // turn it is, or activates the GameOverActivity.
    public void submitLetterButton(View v) {
        letterGuess = submitLetter();
        wordGuess = game.guess(letterGuess);
        if (!game.ended()) {
            setWord(wordGuess);
            setTurnMessage();
        }
        else {
            winnerPlayer = getWinnerName();
            Intent intent = new Intent(this, GameOverActivity.class);
            intent.putExtra("winnername", winnerPlayer);
            intent.putExtra("finalword", wordGuess);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    // 'setWord' displays the word formed so far to the screen.
    public void setWord(String wordGuess){
        TextView word_so_far = (TextView) findViewById(R.id.word_display);
        word_so_far.setText(" " + wordGuess.toUpperCase());
    }

    // 'setTurnMessage' decides, based on the output of turn(), what the name
    // of the player is that is on turn, and gives a message about who's turn it is.
    public void setTurnMessage(){
        if (game.turn() == 1) {
            player = gamePreferences.getString("player1", "UNKNOWN PLAYER");
        } else {
            player = gamePreferences.getString("player2", "UNKNOWN PLAYER");
        }
        TextView whos_turn = (TextView) findViewById(R.id.whos_turn_display);
        whos_turn.setText(getResources().getString(R.string.beginzin) + " " + player + " " + getResources().getString(R.string.eindzin));
    }

    // 'submitLetter' retrieves the letter that the player has submitted, and
    // clears the inputfield for the next player.
    private String submitLetter(){
        editTextNewLetter = (EditText) findViewById(R.id.new_letter);
        inputLetter = editTextNewLetter.getText().toString();
        editTextNewLetter.setText("");
        return inputLetter;
    }

    // 'getWinnerName' retrieves the name of the winner.
    public String getWinnerName(){
        winnerInt = game.winner();
        if (winnerInt == 1){
            winnerPlayer = gamePreferences.getString("player1", "UNKNOWN PLAYER");
        } else {
            winnerPlayer = gamePreferences.getString("player2", "UNKNOWN PLAYER");
        }
        return winnerPlayer;
    }

    // 'onBackPressed' is activated when the user presses the back button on the telephone. It
    // activates a confirmation dialog that asks whether the player is sure he/she wants to leave
    // the game.
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getResources().getString(R.string.dialog_message));
        builder.setTitle(getResources().getString(R.string.dialog_name));

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    // 'onClick' is required for this activity class, but is not used.
    @Override
    public void onClick(View v){
    }

}
