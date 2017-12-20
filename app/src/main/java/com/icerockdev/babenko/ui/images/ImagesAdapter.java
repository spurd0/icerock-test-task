package com.icerockdev.babenko.ui.images;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.icerockdev.babenko.R;
import com.icerockdev.babenko.model.entities.ImageItem;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Roman Babenko on 11/05/17.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesItemHolder> {
    private ImagesListCallback mCallback;
    private List<ImageItem> mImageList;

    public ImagesAdapter(List<ImageItem> imageList, ImagesListCallback callback) {
        mCallback = callback;
        mImageList = imageList;
    }

    @Override
    public ImagesItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_element, parent, false);
        return new ImagesItemHolder(view);
    }

    @Override
    public void onBindViewHolder(ImagesItemHolder holder, int position) {
        holder.updateView(mImageList.get(position));
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    class ImagesItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.pictureElementId)
        TextView mPictureElementId;

        @BindView(R.id.pictureElementTitle)
        TextView mPictureElementTitle;

        @BindView(R.id.pictureElementImgView)
        ImageView mPictureElementImgView;

        public ImagesItemHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        private void loadImage(String v) {
            Picasso.with(mPictureElementImgView.getContext()).load(v).error(R.drawable.question_mark)
                    .placeholder(R.drawable.question_mark).into(mPictureElementImgView);
        }

        public void updateView(final ImageItem item) {
            mPictureElementId.setText(String.valueOf(item.getId()));
            mPictureElementTitle.setText(item.getTitle());
            loadImage(item.getThumbnailUrl());
            mPictureElementTitle.getRootView().setOnClickListener(view ->
                    mCallback.itemClicked(item.getUrl()));
        }

    }
}
