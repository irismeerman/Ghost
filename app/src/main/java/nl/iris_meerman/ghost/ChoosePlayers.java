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
    StartActivity startobj;

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

                //Intent intent = new Intent(ChoosePlayers.this, StartActivity.class);
                //intent.putExtra("playerchosen", playerselected);
                //startActivity(intent);

                SharedPreferences gameprefs = getSharedPreferences("gameprefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = gameprefs.edit();
                if (!gameprefs.contains("player1")) {
                    editor.putString("player1", playerselected);
                } else if (!gameprefs.contains("player2")){
                    editor.putString("player2", playerselected);
                }
                editor.commit();
                finish();
                //startActivity(intent);
                //startobj = new StartActivity();
                //startobj.putChosenName(playerselected);

                //EditText ePlayer1 = (EditText) findViewById(R.id.player1);
                //ePlayer1.setText(playerselected);

                //startobj.putChosenName();
            }
        });
    }

    @Override
    public void onClick(View v) {
    }
}
