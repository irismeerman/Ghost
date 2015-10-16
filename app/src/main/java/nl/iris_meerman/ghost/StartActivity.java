package nl.iris_meerman.ghost;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import java.util.ArrayList;

/* StartActivity.java
 * This activity is started when the app is opened. It displays the name of the game; Ghost,
 * and it displays two input fields where playernames can be filled in and a start button.
 * There is also a menu button with which the 'Language settings' or 'Select player' activities
 * can be started.
 */

public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextPlayer1, editTextPlayer2;
    String namePlayer1, namePlayer2;
    SharedPreferences gamePreferences;
    DatabaseHandler db;
    ArrayList<String> playerList;
    SharedPreferences.Editor editor;
    public static final String sp = "gamePreferences";

    // 'onCreate' creates the sharedpreferences in which the playernames of the two players that
    // are playing the game are saved (and the language). It also creates the database and editor.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        gamePreferences = getSharedPreferences(sp, Context.MODE_PRIVATE);
        db = new DatabaseHandler(this);
        editor = gamePreferences.edit();
        checkPresentPlayers();
    }

    // 'checkPresentPlayers' checks if there are already players saved in the shared preferences, in
    // that case they need to be displayed in the input fields.
    public void checkPresentPlayers(){
        editTextPlayer1 = (EditText) findViewById(R.id.player1);
        editTextPlayer2 = (EditText) findViewById(R.id.player2);
        if (!gamePreferences.getString("player1", "").equals("")) {
            editTextPlayer1.setText(gamePreferences.getString("player1", ""), EditText.BufferType.EDITABLE);
        }
        if (!gamePreferences.getString("player2", "" ).equals("")){
            editTextPlayer2.setText(gamePreferences.getString("player2", ""), EditText.BufferType.EDITABLE);
        }
    }

    // 'startButton' makes sure that the given names are saved in the sharedpreference and
    // starts the game activity.
    public void startButton(View v){
        setPlayerNames();
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

    // 'setPlayerNames' changes the player1 and player2 entry in the sharedpreferences to the given
    // names by the user. It also makes sure it is saved in the database.
    public void setPlayerNames(){
        namePlayer1 = editTextPlayer1.getText().toString();
        namePlayer2 = editTextPlayer2.getText().toString();

        editor.putString("player1", namePlayer1);
        editor.putString("player2", namePlayer2);
        editor.commit();

        playerInDatabase(namePlayer1);
        playerInDatabase(namePlayer2);
    }

    // 'playerInDatabase' adds the playername to the database if it is not yet present in the
    // database.
    public void playerInDatabase(String name){
        playerList = db.getAllPlayers();
        if (!playerList.contains(name)){
            db.addPlayer(name);
        }
    }

    // 'onCreateOptionsMenu' makes sure that the menu in the upper right corner is enabled.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_startactivity, menu);
        return true;
    }

    // 'onOptionItemSelected' makes sure that when one of the items in the menu is selected,
    // the activity is started. In case of the SelectPlayerActivity is started, it then makes
    // sure that the playernames given so far are stored in the sharedpreferences.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.lang_settings_id) {
            Intent intent = new Intent(this, SelectLanguageActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.choose_players_id){
            Intent intent = new Intent(this, SelectPlayerActivity.class);
            namePlayer1 = editTextPlayer1.getText().toString();
            namePlayer2 = editTextPlayer2.getText().toString();
            editor.putString("player1", namePlayer1);
            editor.putString("player2", namePlayer2);
            editor.commit();
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // 'onClick' is required for this activity class, but is not used.
    public void onClick(View v){
    }
}
