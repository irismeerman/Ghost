package nl.iris_meerman.ghost;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChoosePlayers extends AppCompatActivity implements View.OnClickListener {

    DatabaseHandler db;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseplayers);
        db = new DatabaseHandler(this);
        ArrayList<String> playersList = db.getAllPlayers();

        final ListView playerlistview = (ListView) findViewById(R.id.listViewPlayers);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getApplicationContext(),
                android.R.layout.simple_list_item_activated_1, playersList);

        playerlistview.setAdapter(adapter);

        playerlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object objectplayer = playerlistview.getItemAtPosition(position);
                String playerselected = String.valueOf(objectplayer);//As you are using Default String Adapter
                Toast.makeText(getBaseContext(), playerselected, Toast.LENGTH_SHORT).show();

                SharedPreferences gameprefs = getSharedPreferences("gameprefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = gameprefs.edit();

                if (gameprefs.getString("player1", "").isEmpty()){
                    //EditText ePlayer1 = (EditText) findViewById(R.id.player1);
                    //ePlayer1.setText(playerselected, EditText.BufferType.EDITABLE);
                    editor.putString("player1", playerselected);
                }
                else if (gameprefs.getString("player2", "").isEmpty()){
                    //EditText ePlayer2 = (EditText) findViewById(R.id.player2);
                    //ePlayer2.setText(playerselected, EditText.BufferType.EDITABLE);
                    editor.putString("player2", playerselected);
                }
                editor.commit();
                Log.d("test with setting names", "whoop");
                Intent intent = new Intent(ChoosePlayers.this, start.class);
                //intent.putExtra("playerchosen", playerselected);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
