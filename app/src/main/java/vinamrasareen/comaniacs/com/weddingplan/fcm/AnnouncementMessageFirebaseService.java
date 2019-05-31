package vinamrasareen.comaniacs.com.weddingplan.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import vinamrasareen.comaniacs.com.weddingplan.MainActivity;
import vinamrasareen.comaniacs.com.weddingplan.MessageData;
import vinamrasareen.comaniacs.com.weddingplan.R;
import vinamrasareen.comaniacs.com.weddingplan.provider.AnnouncementContract;
import vinamrasareen.comaniacs.com.weddingplan.provider.AnnouncementProvider;

public class AnnouncementMessageFirebaseService extends FirebaseMessagingService {

    private static final String JSON_KEY_MESSAGE = AnnouncementContract.COLUMN_MESSAGE;
    private static final String JSON_KEY_DATE = AnnouncementContract.COLUMN_DATE;
    private static final int NOTIFICATION_MAX_CHARACTERS = 30;
    private static SharedPreferences app_preferences;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean notification = app_preferences.getBoolean(getResources().getString(R.string.pref_notification_key), getResources().getBoolean(R.bool.notification_default));

        Map<String, String> data = remoteMessage.getData();

        if (data.size() > 0) {

            insertAnnouncement(data);
            // Send a notification that you got a new message
            if (notification) {
                sendNotification(data);
            }

        }
    }

    public void message(Context mContext ,Set msg, Bundle bundle) {

        Map<String, String> data = new HashMap<>();

        for (Object key : msg) {
            if (key.equals("message")) {
                MessageData messageData = new MessageData();
                messageData.setKeyName(String.valueOf(key));
                messageData.setValue(bundle.getString(String.valueOf(key)));
                data.put(messageData.getKeyName(), messageData.getValue());
            }
        }

        if (data.size() > 0) {

            insertCustomAnnouncement(data, mContext);

            sendCustomNotification(data, mContext);

        }
    }

    private void sendCustomNotification(Map<String, String> data, Context mContext) {
        Intent intent = new Intent(mContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String message = data.get(JSON_KEY_MESSAGE);

        if (message.length() > NOTIFICATION_MAX_CHARACTERS) {
            message = message.substring(0, NOTIFICATION_MAX_CHARACTERS) + "\u2026";
        }

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(mContext)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(String.format(mContext.getResources().getString(R.string.notification_message)))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private void sendNotification(Map<String, String> data) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String message = data.get(JSON_KEY_MESSAGE);

        if (message.length() > NOTIFICATION_MAX_CHARACTERS) {
            message = message.substring(0, NOTIFICATION_MAX_CHARACTERS) + "\u2026";
        }

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(String.format(getString(R.string.notification_message)))
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    public void insertAnnouncement(final Map<String, String> data) {

        AsyncTask<Void, Void, Void> insertAnnouncementTask = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                ContentValues newMessage = new ContentValues();
                newMessage.put(AnnouncementContract.COLUMN_MESSAGE, data.get(JSON_KEY_MESSAGE).trim());
                newMessage.put(AnnouncementContract.COLUMN_DATE, data.get(JSON_KEY_DATE));
                getContentResolver().insert(AnnouncementProvider.AnnouncementMessages.CONTENT_URI, newMessage);
                return null;
            }
        };

        insertAnnouncementTask.execute();
    }

    public void insertCustomAnnouncement(final Map<String, String> data, Context mContext) {

        AsyncTask<Void, Void, Void> insertAnnouncementTask = new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                ContentValues newMessage = new ContentValues();
                newMessage.put(AnnouncementContract.COLUMN_MESSAGE, data.get(JSON_KEY_MESSAGE).trim());
                newMessage.put(AnnouncementContract.COLUMN_DATE, data.get(JSON_KEY_DATE));
                mContext.getContentResolver().insert(AnnouncementProvider.AnnouncementMessages.CONTENT_URI, newMessage);
                return null;
            }
        };

        insertAnnouncementTask.execute();
    }

}
