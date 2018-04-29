package com.icerockdev.babenko.ui.images;

import android.content.Context;
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
        LayoutInflater inflater = (LayoutInflater) parent.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.item_image, parent, false);
        return new ImagesItemHolder(convertView);
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
        @BindView(R.id.pictureElementImageView)
        ImageView pictureElementImageView;
        @BindView(R.id.pictureElementId)
        TextView pictureElementId;
        @BindView(R.id.pictureElementTitle)
        TextView pictureElementTitle;

        public ImagesItemHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void updateView(final ImageItem item) {
            Picasso.get().load(item.getThumbnailUrl()).error(R.drawable.question_mark)
                    .placeholder(R.drawable.question_mark).into(pictureElementImageView);
            pictureElementId.setText(String.valueOf(item.getId()));
            pictureElementTitle.setText(item.getTitle());
        }

    }
}
