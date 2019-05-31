package vinamrasareen.comaniacs.com.weddingplan.adapter;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import vinamrasareen.comaniacs.com.weddingplan.MainActivity;
import vinamrasareen.comaniacs.com.weddingplan.R;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.AnnouncementViewHolder> {

    private Cursor mData;
    private static SimpleDateFormat sDateFormat = new SimpleDateFormat("dd MMM");


    private static final long MINUTE_MILLIS = 1000 * 60;
    private static final long HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final long DAY_MILLIS = 24 * HOUR_MILLIS;

    @NonNull
    @Override
    public AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_announcement_layout, viewGroup, false);

        AnnouncementViewHolder vh = new AnnouncementViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementViewHolder announcementViewHolder, int position) {

        Picasso.get().load(R.drawable.marriage)
                .fit()
                .transform(new CropCircleTransformation())
                .into(announcementViewHolder.mAuthorImageView);

        mData.moveToPosition(position);

        String message = mData.getString(MainActivity.COL_NUM_MESSAGE);
        long dateMillis = mData.getLong(MainActivity.COL_NUM_DATE);
        String date = "";
        long now = System.currentTimeMillis();

        // Change how the date is displayed depending on whether it was written in the last minute,
        // the hour, etc.
        if (now - dateMillis < (DAY_MILLIS)) {
            if (now - dateMillis < (HOUR_MILLIS)) {
                long minutes = Math.round((now - dateMillis) / MINUTE_MILLIS);
                date = String.valueOf(minutes) + "m";
            } else {
                long minutes = Math.round((now - dateMillis) / HOUR_MILLIS);
                date = String.valueOf(minutes) + "h";
            }
        } else {
            Date dateDate = new Date(dateMillis);
            date = sDateFormat.format(dateDate);
        }

        // Add a dot to the date string
        date = "\u2022 " + date;

        announcementViewHolder.messageTextView.setText(message);

    }

    @Override
    public int getItemCount() {
        if (mData == null) return 0;
        return mData.getCount();
    }

    public void swapCursor(Cursor newCursor){
        mData = newCursor;
        notifyDataSetChanged();
    }


    public class AnnouncementViewHolder extends RecyclerView.ViewHolder{

        final TextView messageTextView;
        final TextView dateTextView;
        final ImageView mAuthorImageView;

        public AnnouncementViewHolder(@NonNull View itemView) {
            super(itemView);
            messageTextView = itemView.findViewById(R.id.message_text_view);
            dateTextView = itemView.findViewById(R.id.date_text_view);
            mAuthorImageView = itemView.findViewById(R.id.author_image_view);
        }
    }

}
