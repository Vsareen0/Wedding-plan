package vinamrasareen.comaniacs.com.weddingplan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.messaging.FirebaseMessaging;

import vinamrasareen.comaniacs.com.weddingplan.adapter.AnnouncementAdapter;
import vinamrasareen.comaniacs.com.weddingplan.provider.AnnouncementContract;
import vinamrasareen.comaniacs.com.weddingplan.provider.AnnouncementProvider;
import vinamrasareen.comaniacs.com.weddingplan.puzzle.PuzzleActivity;
import vinamrasareen.comaniacs.com.weddingplan.theme.Constant;

import static vinamrasareen.comaniacs.com.weddingplan.SplashActivity.key;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, SharedPreferences.OnSharedPreferenceChangeListener{

    private static final int LOADER_ID_MESSAGES = 0;
    private static final String SUBJECT = "Join the Wedding Plan";
    private static final String EXTRA_TEXT = "http://www.theweddingplan.com";
    private static final String SHARE_LINK = "Share link!";

    RecyclerView mRecyclerView;

    LinearLayoutManager mLayoutManager;
    AnnouncementAdapter mAdapter;
    Constant constant;
    int appTheme;
    int themeColor;
    int appColor;


    static final String[] MESSAGES_PROJECTION = {
            AnnouncementContract.COLUMN_MESSAGE,
            AnnouncementContract.COLUMN_DATE
    };

    public static final int COL_NUM_MESSAGE = 0;
    public static final int COL_NUM_DATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupSharedPreferences();
        setContentView(R.layout.activity_main);
        FirebaseMessaging.getInstance().subscribeToTopic(key);

        mRecyclerView = findViewById(R.id.announcements_recycler_view);

        setupAnnouncementAdapter();
        getSupportLoaderManager().initLoader(LOADER_ID_MESSAGES, null, this);
    }

    private void setupAnnouncementAdapter(){
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                mRecyclerView.getContext(),
                mLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mAdapter = new AnnouncementAdapter();
        mRecyclerView.setAdapter(mAdapter);

    }

    private void setupSharedPreferences(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        appColor = sharedPreferences.getInt("color", 0);
        appTheme = sharedPreferences.getInt("theme", 0);
        themeColor = appColor;
        constant.color = appColor;
        Boolean night_mode = sharedPreferences.getBoolean(getResources().getString(R.string.night_mode_key), getResources().getBoolean(R.bool.night_mode_default));
        String change_theme = sharedPreferences.getString(getResources().getString(R.string.pref_theme_key), getResources().getString(R.string.pref_theme_default));

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

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
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
    protected void onStart() {
        super.onStart();
        setupSharedPreferences();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                Intent settingsActivity = new Intent(this, SettingsActivity.class);
                startActivity(settingsActivity);
                break;
            case R.id.Gallery:
                Intent galleryActivity = new Intent(this, GalleryActivity.class);
                startActivity(galleryActivity);
                break;
            case R.id.puzzle:
                Intent puzzleActivity = new Intent(this, PuzzleActivity.class);
                startActivity(puzzleActivity);
                break;
            case R.id.Invite:
                shareTextUrl();
                default: break;
        }
        return true;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new CursorLoader(this, AnnouncementProvider.AnnouncementMessages.CONTENT_URI,
                MESSAGES_PROJECTION, null, null, AnnouncementContract.COLUMN_DATE + " DESC");
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
            mAdapter.swapCursor(null);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(R.string.night_mode_key)){
            if (sharedPreferences.getBoolean(key, getResources().getBoolean(R.bool.night_mode_default))){
                setTheme(R.style.NightMode);
            }
        }
    }


    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        // Add data to the intent, the receiving app will decide
        // what to do with it.
        share.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
        share.putExtra(Intent.EXTRA_TEXT, EXTRA_TEXT);

        startActivity(Intent.createChooser(share, SHARE_LINK));
    }
}
