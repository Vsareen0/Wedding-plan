package vinamrasareen.comaniacs.com.weddingplan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import vinamrasareen.comaniacs.com.weddingplan.theme.Constant;

public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
    SharedPreferences app_preferences;
    SharedPreferences.Editor editor;
    Button button;
    int appTheme;
    int themeColor;
    int appColor;
    Constant constant;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        appColor = app_preferences.getInt("color", 0);
        appTheme = app_preferences.getInt("theme", 0);
        Boolean night_mode = app_preferences.getBoolean(getResources().getString(R.string.night_mode_key), getResources().getBoolean(R.bool.night_mode_default));
        String change_theme = app_preferences.getString(getResources().getString(R.string.pref_theme_key), getResources().getString(R.string.pref_theme_default));
        themeColor = appColor;
        constant.color = appColor;
        editor = app_preferences.edit();

        if (themeColor == 0){
            setTheme(Constant.theme);
        }else if (appTheme == 0){
            setTheme(Constant.theme);
        }else{
            setTheme(appTheme);
        }


        if (night_mode){
            setTheme(R.style.NightMode);
        }else{
            changeTheme(change_theme);
        }
        editor.apply();

        setContentView(R.layout.activity_settings);

        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

}

    private void changeTheme(String theme){
        switch (theme){
            case "red":
                setTheme(R.style.AppTheme_red);
                break;
            case "blue":
                setTheme(R.style.AppTheme_blue);
                break;
            case "sky_blue":
                setTheme(R.style.AppTheme_skyblue);
                break;
            case "pink":
                setTheme(R.style.AppTheme_pink);
                break;
            case "dark_pink":
                setTheme(R.style.AppTheme_dark_Pink);
                break;
            case "violet":
                setTheme(R.style.AppTheme_violet);
                break;
            case "green":
                setTheme(R.style.AppTheme_green);
                break;
            case "grey":
                setTheme(R.style.AppTheme_grey);
                break;
            case "brown":
                setTheme(R.style.AppTheme_brown);
                break;
            default: setTheme(R.style.AppTheme);
                break;
        }
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    if (key.equals(getResources().getString(R.string.night_mode_key))){
        Intent startSettings = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(startSettings);
    }
    }
}
