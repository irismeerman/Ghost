package nl.iris_meerman.ghost;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Locale;

/**
 * Created by iris on 9-10-15.
 */
public class LanguageSettings extends AppCompatActivity implements View.OnClickListener {

    Context context;
    String lexiconLanguage;
    StartActivity start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.languagesettings);
    }

    public void onClick(View v){
        Locale locale;
        locale = new Locale("en");
        switch (v.getId()){
            case R.id.nederlands_button: {
                locale = new Locale("nl");
                changeLanguage("nl");
                break;
            }
            case R.id.english_button: {
                locale = new Locale("en");
                changeLanguage("en");
                break;
            }
        }
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, null);

        Intent intent = new Intent(this, StartActivity.class);
        finish();
        startActivity(intent);

    }

    public void changeLanguage(String lang){
        SharedPreferences gameprefs = getSharedPreferences("gameprefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = gameprefs.edit();
        editor.putString("language", lang);
        editor.commit();

    }


}
