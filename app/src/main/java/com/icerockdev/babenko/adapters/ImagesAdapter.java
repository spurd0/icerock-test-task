package com.icerockdev.babenko.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.interfaces.ImagesListCallback;
import com.icerockdev.babenko.model.ImageItem;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Roman Babenko on 11/05/17.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesItemHolder> {
    private ImagesListCallback mCallback;
    private ArrayList<ImageItem> mImageList;
    private Picasso mPicasso;


    public ImagesAdapter(Context context, ArrayList<ImageItem> imageList, ImagesListCallback callback) {
        this.mCallback = callback;
        this.mImageList = imageList;
        mPicasso = new Picasso.Builder(context).listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                if (BuildConfig.DEBUG) exception.printStackTrace();
            }
        }).downloader(new OkHttpDownloader(context)).build();
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

        public ImagesItemHolder(View itemView) {
            super(itemView);
            this.mId = (TextView) itemView.findViewById(R.id.pictureElementId);
            this.mTitle = (TextView) itemView.findViewById(R.id.pictureElementTitle);
            this.mImageView = (ImageView) itemView.findViewById(R.id.pictureElementImgView);
        }

        public void updateView(final ImageItem item, final ImagesListCallback callback) {
            mId.setText(String.valueOf(item.getId()));
            mTitle.setText(String.valueOf(item.getTitle()));
            mPicasso.load(item.getThumbnailUrl()).into(mImageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.itemClicked(item.getUrl());
                }
            });
        }

    }
}
