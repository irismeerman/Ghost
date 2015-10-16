package nl.iris_meerman.ghost;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import java.util.Locale;

/* SelectLanguageActivity.java
 * This activity is accessible via the menu at the start activity. You can select English or Dutch.
 * When selected, you automatically return to the start screen.
 */

public class SelectLanguageActivity extends AppCompatActivity implements View.OnClickListener {

    Locale locale;
    Configuration config;
    Intent intent;
    SharedPreferences gamePreferences;
    SharedPreferences.Editor editor;
    public static final String sp = "gamePreferences";

    // 'onCreate' sets the layout (two buttons; English or Dutch)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.languagesettings);
    }

    // 'onClick' responds to the 'Nederlands' or 'English' button. The configuration is then changed
    // to this language. The configuration also decides which strings.xml file is used.
    // After this, the user returns to the start activity.
    public void onClick(View v){
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
        config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, null);

        backToStartActivity();
    }

    // 'changeLanguage' changes the language in the sharedpreferences file.
    public void changeLanguage(String lang){
        gamePreferences = getSharedPreferences(sp, Context.MODE_PRIVATE);
        editor = gamePreferences.edit();
        editor.putString("language", lang);
        editor.commit();
    }

    public void backToStartActivity(){
        intent = new Intent(this, StartActivity.class);
        finish();
        startActivity(intent);
    }
}
