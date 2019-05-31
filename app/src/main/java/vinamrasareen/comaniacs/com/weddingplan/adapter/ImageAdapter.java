package vinamrasareen.comaniacs.com.weddingplan.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import vinamrasareen.comaniacs.com.weddingplan.R;
import vinamrasareen.comaniacs.com.weddingplan.Upload;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Upload> mUploads;

    public ImageAdapter(Context context, List<Upload> uploadList){
        mContext = context;
        mUploads = uploadList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_gallery_layout, viewGroup, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int position) {
        Upload upload = mUploads.get(position);

            Picasso.get().load(R.drawable.marriage)
                    .fit()
                    .transform(new CropCircleTransformation())
                    .into(holder.mMarriageImage);


        Picasso.get().load(upload.getImageUrl())
                .fit()
                .error(R.drawable.ic_error_black_24dp)
                .centerCrop()
                .into(holder.mGalleryImage, new com.squareup.picasso.Callback(){

                    @Override
                    public void onSuccess() {
                        if (holder.mImageProgress != null){
                            holder.mImageProgress.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{

        public ImageView mGalleryImage,mMarriageImage;
        public ProgressBar mImageProgress;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            mGalleryImage = itemView.findViewById(R.id.gallery_image);
            mImageProgress = itemView.findViewById(R.id.progress_image);
            mMarriageImage = itemView.findViewById(R.id.marriage);
        }
    }

}
