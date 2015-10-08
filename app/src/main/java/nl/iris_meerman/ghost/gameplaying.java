package nl.iris_meerman.ghost;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.net.URL;

public class gameplaying extends AppCompatActivity implements View.OnClickListener {

    Lexicon lexicon;
    SharedPreferences gameprefs;
    EditText eNewLetter;
    String player, new_letter, letterGuess, wordGuess, winnerplayer;
    int winnerInt;
    boolean buttonClicked = false;
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_playing);
        lexicon = new Lexicon(R.raw.english, this);
        gameprefs = getSharedPreferences("gameprefs", Context.MODE_PRIVATE);
        game = new Game(lexicon);
        startGame();
    }

    public void startGame() {
        setTurnMessage();
    }

    public void setWord(String wordGuess){
        TextView word_so_far = (TextView) findViewById(R.id.word_display);
        word_so_far.setText(" " + wordGuess);
    }

    public void setTurnMessage(){
        if (game.turn() == 1) {
            player = gameprefs.getString("player1", "UNKNOWN PLAYER");
        } else {
            player = gameprefs.getString("player2", "UNKNOWN PLAYER");
        }
        TextView whos_turn = (TextView) findViewById(R.id.whos_turn_display);
        whos_turn.setText("It's " + player + "'s turn to choose the next letter");
    }

    private String submitLetter(){
        eNewLetter = (EditText) findViewById(R.id.new_letter);
        new_letter = eNewLetter.getText().toString();
        eNewLetter.setText("");
        return new_letter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_letter:
                letterGuess = submitLetter();
                wordGuess = game.guess(letterGuess);
                if (game.ended() == false) {
                    setWord(wordGuess);
                    setTurnMessage();
                }
                else {
                    winnerplayer = getWinnerName();
                    Log.d("test winner: ", winnerplayer);
                    Intent intent = new Intent(this, Highscores.class);
                    intent.putExtra("winnername", winnerplayer);
                    intent.putExtra("finalword", wordGuess);
                    startActivity(intent);
                }
                break;
        }
    }

    public String getWinnerName(){
        winnerInt = game.winner();
        if (winnerInt == 1){
            winnerplayer = gameprefs.getString("player1", "UNKNOWN PLAYER");
        } else {
            winnerplayer = gameprefs.getString("player2", "UNKNOWN PLAYER");
        }
        return winnerplayer;
    }
}
