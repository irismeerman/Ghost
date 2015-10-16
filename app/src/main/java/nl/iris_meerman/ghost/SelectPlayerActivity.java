package nl.iris_meerman.ghost;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;

/* SelectPlayerActivity.java
 * This activity can be entered via the menu button at the home screen. It displays all previously
 * entered user names which you can select as player name. You then return to the start screen in
 * which one of the player name inputfields is filled with the selected name.
*/
public class SelectPlayerActivity extends AppCompatActivity implements View.OnClickListener {

    DatabaseHandler db;
    ArrayList<String> playersList;
    ListView playersListView;
    ArrayAdapter<String> adapter;
    Object playerObject;
    String selectedPlayer;
    SharedPreferences gamePreferences;
    public static final String sp = "gamePreferences";

    // 'onCreate' sets the layout and takes care of direct reaction when a field in the list
    // (a playername) is selected. When one of the players in the list is selected, the player
    // is saved in the shared preferences and the user returns automatically to the start activity,
    // where the selected playername is displayed in one of the inputfields. It fills in the name in
    // the first or second field name, depending on which is empty. If both already filled, it
    // overwrites the first name.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseplayers);

        db = new DatabaseHandler(this);
        gamePreferences = getSharedPreferences(sp, Context.MODE_PRIVATE);
        displayListOfPlayers();
        final SharedPreferences.Editor editor = gamePreferences.edit();

        playersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                playerObject = playersListView.getItemAtPosition(position);
                selectedPlayer = String.valueOf(playerObject);

                if (gamePreferences.getString("player1", "").equals("")) {
                    editor.putString("player1", selectedPlayer);
                } else if (gamePreferences.getString("player2", "").equals("")){
                    editor.putString("player2", selectedPlayer);
                } else {
                    editor.putString("player1", selectedPlayer);
                }
                editor.commit();
                Intent intent = new Intent(SelectPlayerActivity.this, StartActivity.class);
                startActivity(intent);
            }
        });
    }

    // 'displayListOfPlayers' calls the database to select all previously used players. It then
    // sets this list to the screen via an arrayadapter.
    public void displayListOfPlayers(){
        playersList = db.getAllPlayers();
        playersListView = (ListView) findViewById(R.id.listViewPlayers);
        adapter = new ArrayAdapter<>(this.getApplicationContext(),
                android.R.layout.simple_list_item_activated_1, playersList);
        playersListView.setAdapter(adapter);
    }

    // 'onClick' is required for this activity class, but is not used.
    @Override
    public void onClick(View v) {
    }
}
