package vinamrasareen.comaniacs.com.weddingplan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.Set;

import vinamrasareen.comaniacs.com.weddingplan.fcm.AnnouncementMessageFirebaseService;

public class SplashActivity extends AppCompatActivity implements View.OnClickListener{
    public static final String key = "announcements";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            Set data = getIntent().getExtras().keySet();
            AnnouncementMessageFirebaseService announcementMessageFirebaseService = new AnnouncementMessageFirebaseService();
            announcementMessageFirebaseService.message(getApplicationContext(), data, bundle);
        }

    }

    @Override
    public void onClick(View v) {
        Intent startMainActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(startMainActivity);
    }
}
