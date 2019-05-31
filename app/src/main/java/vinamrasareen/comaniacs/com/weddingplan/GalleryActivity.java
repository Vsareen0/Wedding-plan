package vinamrasareen.comaniacs.com.weddingplan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import vinamrasareen.comaniacs.com.weddingplan.adapter.ImageAdapter;
import vinamrasareen.comaniacs.com.weddingplan.puzzle.PuzzleActivity;
import vinamrasareen.comaniacs.com.weddingplan.theme.Constant;

public class GalleryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;

    private ProgressBar mProgressBarCircle;
    private DatabaseReference mDatabaseRef;
    private List<Upload> mUploads;
    Constant constant;
    SharedPreferences.Editor editor;
    SharedPreferences app_preferences;
    int appTheme;
    int themeColor;
    int appColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        appColor = app_preferences.getInt("color", 0);
        appTheme = app_preferences.getInt("theme", 0);
        themeColor = appColor;
        constant.color = appColor;
        Boolean night_mode = app_preferences.getBoolean(getResources().getString(R.string.night_mode_key), getResources().getBoolean(R.bool.night_mode_default));
        String change_theme = app_preferences.getString(getResources().getString(R.string.pref_theme_key), getResources().getString(R.string.pref_theme_default));

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
            setTheme(Constant.theme);
        }

        if (night_mode){
            setTheme(R.style.NightMode);
        }else{
//            setTheme(Constant.theme);
            changeTheme(change_theme);
        }

        editor = app_preferences.edit();
        editor.apply();

        setContentView(R.layout.activity_gallery);

        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mProgressBarCircle = findViewById(R.id.progress_circle);
        recyclerView = findViewById(R.id.gallery_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mUploads = new ArrayList<>();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        imageAdapter = new ImageAdapter(getApplicationContext(), mUploads);
        recyclerView.setAdapter(imageAdapter);
        mProgressBarCircle.setVisibility(View.INVISIBLE);


        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUploads.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    Upload upload = postSnapshot.getValue(Upload.class);
                    mUploads.add(upload);
                }

                imageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(GalleryActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressBarCircle.setVisibility(View.INVISIBLE);
            }
        });
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.gallery_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
            case R.id.settings:
                Intent settingsIntent =new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                break;
            case R.id.puzzle:
                Intent puzzleIntent =new Intent(this, PuzzleActivity.class);
                startActivity(puzzleIntent);
                break;
            case R.id.notifications:
                Intent notificationIntent =new Intent(this, MainActivity.class);
                startActivity(notificationIntent);
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

}
