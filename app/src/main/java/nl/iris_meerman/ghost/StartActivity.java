package nl.iris_meerman.ghost;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Iterator;

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    EditText ePlayer1, ePlayer2;
    String namePlayer1, namePlayer2;
    SharedPreferences gameprefs;
    DatabaseHandler db;
    Iterator<String> it;
    ArrayList<String> playerlist;
    Context context;
    SharedPreferences.Editor editor;
    public static final String sp = "gameprefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        gameprefs = getSharedPreferences(sp, Context.MODE_PRIVATE);
        db = new DatabaseHandler(this);
        editor = gameprefs.edit();
        ePlayer1 = (EditText) findViewById(R.id.player1);
        ePlayer2 = (EditText) findViewById(R.id.player2);

    }

    public void onClick(View v){
        setPlayerNames();
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    public void setPlayerNames(){
        Log.d("test: ", "setplayernames");
        //ePlayer1 = (EditText) findViewById(R.id.player1);
        //ePlayer2 = (EditText) findViewById(R.id.player2);
        namePlayer1 = ePlayer1.getText().toString();
        Log.d("test player1 name: ", namePlayer1);
        namePlayer2 = ePlayer2.getText().toString();

        //editor = gameprefs.edit();
        editor.putString("player1", namePlayer1);
        editor.putString("player2", namePlayer2);
        editor.commit();

        playerInHighscores(namePlayer1);
        playerInHighscores(namePlayer2);
        Log.d("test playerlist: ", String.valueOf(playerlist));
    }

    // als de speler(s) nog niet voorkomen in de highscoreslist,
    // worden ze toegevoegd.
    public void playerInHighscores(String name){
        playerlist = db.getAllPlayers();
        if (!playerlist.contains(name)){
            db.addPlayer(name);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, LanguageSettings.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.choose_players){
            Intent intent = new Intent(this, ChoosePlayers.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onRestart(){
        super.onRestart();
        putChosenName();
    }
    public void onResume(){
        super.onResume();
        putChosenName();
    }

    public void putChosenName(){
        //editor = gameprefs.edit();
        //SharedPreferences gameprefs = getSharedPreferences("gameprefs", Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = gameprefs.edit();
        //if (gameprefs.contains("player1")){
        //EditText ePlayer1 = (EditText) findViewById(R.id.player1);
        String playerName = gameprefs.getString("player1", "");
        ePlayer1 = (EditText) findViewById(R.id.player1);
        ePlayer1.setText(playerName, EditText.BufferType.EDITABLE);
        //editor.putString("player1", playerselected);

        //} else if (!gameprefs.contains("player2")) {
        //EditText ePlayer2 = (EditText) findViewById(R.id.player2);
        //ePlayer2.setText(playerselected, EditText.BufferType.EDITABLE);
        //editor.putString("player2", playerselected);
        //}
        //editor.commit();
    }
}
