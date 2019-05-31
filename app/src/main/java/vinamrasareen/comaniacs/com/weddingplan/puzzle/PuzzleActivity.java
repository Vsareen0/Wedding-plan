package vinamrasareen.comaniacs.com.weddingplan.puzzle;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import vinamrasareen.comaniacs.com.weddingplan.R;
import vinamrasareen.comaniacs.com.weddingplan.adapter.CustomAdapter;
import vinamrasareen.comaniacs.com.weddingplan.theme.Constant;

public class PuzzleActivity extends AppCompatActivity {

    private static GestureDetectGridView mGridView;

    private static final int COLUMNS = 3;
    private static final int DIMENSIONS = COLUMNS * COLUMNS;

    private static int mColumnWidth, mColumnHeight;

    private static ArrayList<Button> buttons;

    SharedPreferences app_preferences;
    SharedPreferences.Editor editor;

    public static final String up = "up";
    public static final String down = "down";
    public static final String left = "left";
    public static final String right = "right";
    private static String[] tileList;

    int appTheme;
    int themeColor;
    int appColor;
    Constant constant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean night_mode = app_preferences.getBoolean(getResources().getString(R.string.night_mode_key), getResources().getBoolean(R.bool.night_mode_default));
        String change_theme = app_preferences.getString(getResources().getString(R.string.pref_theme_key), getResources().getString(R.string.pref_theme_default));
        themeColor = appColor;
        constant.color = appColor;
        editor = app_preferences.edit();

        if (themeColor == 0) {
            setTheme(Constant.theme);
        } else if (appTheme == 0) {
            setTheme(Constant.theme);
        } else {
            setTheme(appTheme);
        }


        if (night_mode) {
            setTheme(R.style.NightMode);
        } else {
            changeTheme(change_theme);
        }
        editor.apply();
        setContentView(R.layout.activity_puzzle);

        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        init();

        if (savedInstanceState == null) {
            scramble();
        }

        setDimensions(savedInstanceState);

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

    private void init() {
        mGridView =  findViewById(R.id.grid);
        mGridView.setNumColumns(COLUMNS);

        tileList = new String[DIMENSIONS];
        for (int i = 0; i < DIMENSIONS; i++) {
            tileList[i] = String.valueOf(i);
        }
    }

    private void scramble() {
        int index;
        String temp;
        Random random = new Random();

        for (int i = tileList.length - 1; i > 0; i--) {
            index = random.nextInt(i + 1);
            temp = tileList[index];
            tileList[index] = tileList[i];
            tileList[i] = temp;
        }
    }

    private void setDimensions(Bundle saveInstanceState) {
        ViewTreeObserver vto = mGridView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mGridView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int displayWidth = mGridView.getMeasuredWidth();
                int displayHeight = mGridView.getMeasuredHeight();

                int statusbarHeight = getStatusBarHeight(getApplicationContext());
                int requiredHeight = displayHeight - statusbarHeight;

                mColumnWidth = displayWidth / COLUMNS;
                mColumnHeight = requiredHeight / COLUMNS;

                if (saveInstanceState != null){
                    tileList = saveInstanceState.getStringArray("tiles");
                }
                display(getApplicationContext());

            }
        });
    }

    private int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen",
                "android");

        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }

    private static void display(Context context) {
        buttons = new ArrayList<>();
        Button button;

        for (int i = 0; i < tileList.length; i++) {
            button = new Button(context);

            if (tileList[i].equals("0"))
                button.setBackgroundResource(R.drawable.pigeon_piece1);
            else if (tileList[i].equals("1"))
                button.setBackgroundResource(R.drawable.pigeon_piece2);
            else if (tileList[i].equals("2"))
                button.setBackgroundResource(R.drawable.pigeon_piece3);
            else if (tileList[i].equals("3"))
                button.setBackgroundResource(R.drawable.pigeon_piece4);
            else if (tileList[i].equals("4"))
                button.setBackgroundResource(R.drawable.pigeon_piece5);
            else if (tileList[i].equals("5"))
                button.setBackgroundResource(R.drawable.pigeon_piece6);
            else if (tileList[i].equals("6"))
                button.setBackgroundResource(R.drawable.pigeon_piece7);
            else if (tileList[i].equals("7"))
                button.setBackgroundResource(R.drawable.pigeon_piece8);
            else if (tileList[i].equals("8"))
                button.setBackgroundResource(R.drawable.pigeon_piece9);

            buttons.add(button);
        }


        mGridView.setAdapter(new CustomAdapter(buttons, mColumnWidth, mColumnHeight));
    }

    private static void swap(Context context, int currentPosition, int swap) {
        String newPosition = tileList[currentPosition + swap];
        tileList[currentPosition + swap] = tileList[currentPosition];
        tileList[currentPosition] = newPosition;
        display(context);

        if (isSolved()){
            Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();

        }
    }


    public static void moveTiles(Context context, String direction, int position) {

        // Upper-left-corner tile
        if (position == 0) {

            if (direction.equals(right)) swap(context, position, 1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

        // Upper-center tiles
        } else if (position > 0 && position < COLUMNS - 1) {
            if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else if (direction.equals(right)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

        // Upper-right-corner tile
        } else if (position == COLUMNS - 1) {
            if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

        // Left-side tiles
        } else if (position > COLUMNS - 1 && position < DIMENSIONS - COLUMNS &&
                position % COLUMNS == 0) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(right)) swap(context, position, 1);
            else if (direction.equals(down)) swap(context, position, COLUMNS);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

        // Right-side AND bottom-right-corner tiles
        } else if (position == COLUMNS * 2 - 1 || position == COLUMNS * 3 - 1) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(down)) {

                // Tolerates only the right-side tiles to swap downwards as opposed to the bottom-
                // right-corner tile.
                if (position <= DIMENSIONS - COLUMNS - 1) swap(context, position,
                        COLUMNS);
                else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

        // Bottom-left corner tile
        } else if (position == DIMENSIONS - COLUMNS) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(right)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

        // Bottom-center tiles
        } else if (position < DIMENSIONS - 1 && position > DIMENSIONS - COLUMNS) {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(right)) swap(context, position, 1);
            else Toast.makeText(context, "Invalid move", Toast.LENGTH_SHORT).show();

        // Center tiles
        } else {
            if (direction.equals(up)) swap(context, position, -COLUMNS);
            else if (direction.equals(left)) swap(context, position, -1);
            else if (direction.equals(right)) swap(context, position, 1);
            else swap(context, position, COLUMNS);
        }
    }

    private static boolean isSolved() {
        boolean solved = false;

        for (int i = 0; i < tileList.length; i++) {
            if (tileList[i].equals(String.valueOf(i))) {
                solved = true;
            } else {
                solved = false;
                break;
            }
        }

        return solved;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putStringArray("tiles", tileList);
    }
}
