package vinamrasareen.comaniacs.com.weddingplan;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import vinamrasareen.comaniacs.com.weddingplan.provider.AnnouncementContract;
import vinamrasareen.comaniacs.com.weddingplan.provider.AnnouncementProvider;

import static vinamrasareen.comaniacs.com.weddingplan.MainActivity.MESSAGES_PROJECTION;


public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private Cursor cursor;
    Context mContext;
    Intent intent;

    public WidgetDataProvider(Context context, Intent intent) {
        this.mContext = context;
        this.intent = intent;
    }

    private void initCursor(){
        if (cursor != null) {
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();

        cursor = mContext.getContentResolver()
                .query(AnnouncementProvider.AnnouncementMessages.CONTENT_URI,
                        MESSAGES_PROJECTION,
                        null,
                        null,
                        AnnouncementContract.COLUMN_DATE + " DESC");

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onCreate() {
        initCursor();
        if (cursor != null) {
            cursor.moveToFirst();
        }
    }

    @Override
    public void onDataSetChanged() {
        initCursor();
    }

    @Override
    public void onDestroy() {
        cursor.close();
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.item_announcement_layout);
        cursor.moveToPosition(position);
        remoteViews.setTextViewText(R.id.message_text_view,cursor.getString(cursor.getColumnIndex(AnnouncementContract.COLUMN_MESSAGE)));
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


}
