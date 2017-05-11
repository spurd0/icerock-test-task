package com.icerockdev.babenko.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.icerockdev.babenko.R;
import com.icerockdev.babenko.interfaces.ImagesListCallback;
import com.icerockdev.babenko.model.ImageItem;

import java.util.ArrayList;

/**
 * Created by Roman Babenko on 11/05/17.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesItemHolder> {
    private ImagesListCallback mCallback;
    private ArrayList<ImageItem> mImageList;


    public ImagesAdapter(ArrayList<ImageItem> imageList, ImagesListCallback callback) {
        this.mCallback = callback;
        this.mImageList = imageList;
    }

    @Override
    public ImagesItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View imageItemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_element, parent, false);
        return new ImagesItemHolder(imageItemView);
    }

    @Override
    public void onBindViewHolder(ImagesItemHolder holder, int position) {
        holder.updateView(mImageList.get(position), mCallback);
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    class ImagesItemHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mId;
        private TextView mTitle;


//        private int mId;
//        private int mAlbumId;
//        private String mTitle;
//        private String mUrl;
//        private String mThumbnailUrl;
//        private ImagesListCallback mCallback;
//        private View mItemView;

        public ImagesItemHolder(View itemView) {
            super(itemView);
            this.mId = (TextView) itemView.findViewById(R.id.pictureElementId);
            this.mTitle = (TextView) itemView.findViewById(R.id.pictureElementTitle);
            this.mImageView = (ImageView) itemView.findViewById(R.id.pictureElementImgView);


//            this.mAlbumId = item.getAlbumId();
//            this.mId =item.getId();
//            this.mTitle = item.getTitle();
//            this. mUrl = item.getUrl();
//            this.mThumbnailUrl = item.getThumbnailUrl();
//            this.mCallback = callback;
//            this.mItemView = itemView;
        }

        public void updateView(ImageItem item, final ImagesListCallback callback) {
            mId.setText(String.valueOf(item.getId()));
            mTitle.setText(String.valueOf(item.getTitle()));
            //mImageView.setImage(item.getId());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.itemClicked(Integer.valueOf(mId.getText().toString()));
                }
            });
        }

    }
}
