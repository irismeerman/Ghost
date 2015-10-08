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

public class start extends AppCompatActivity implements View.OnClickListener {

    EditText ePlayer1, ePlayer2;
    String namePlayer1, namePlayer2;
    SharedPreferences gameprefs;
    DatabaseHandler db;
    Iterator<String> it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        gameprefs = getSharedPreferences("gameprefs", Context.MODE_PRIVATE);

    }

    public void onClick(View v){
        setPlayerNames();

        Intent intent = new Intent(this, gameplaying.class);
        startActivity(intent);
    }

    public void setPlayerNames(){
        Log.d("test: ", "setplayernames");
        ePlayer1 = (EditText) findViewById(R.id.player1);
        ePlayer2 = (EditText) findViewById(R.id.player2);
        namePlayer1 = ePlayer1.getText().toString();
        Log.d("test player1 name: ", namePlayer1);
        namePlayer2 = ePlayer2.getText().toString();

        SharedPreferences.Editor editor = gameprefs.edit();
        editor.putString("player1", namePlayer1);
        editor.putString("player2", namePlayer2);
        editor.commit();
        Log.d("test : ", "committed");

        playerInHighscores(namePlayer1);
        playerInHighscores(namePlayer2);
    }

    // als de speler(s) nog niet voorkomen in de highscoreslist,
    // worden ze toegevoegd.
    public void playerInHighscores(String name){
        db = new DatabaseHandler(this);
        ArrayList<String> playerlist = db.getAllPlayers();
        if (!playerlist.contains(name)){
            db.addPlayer(name);
            Log.d("test name ", "added to db");
        }
        //for (it = playerlist.iterator(); it.hasNext();  ){
        //    String f = it.next();
        //}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_start, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
