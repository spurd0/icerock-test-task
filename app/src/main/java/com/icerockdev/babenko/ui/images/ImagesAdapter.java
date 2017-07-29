package com.icerockdev.babenko.ui.images;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.icerockdev.babenko.R;
import com.icerockdev.babenko.databinding.ImageElementBinding;
import com.icerockdev.babenko.interfaces.ImagesListCallback;
import com.icerockdev.babenko.model.ImageItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Roman Babenko on 11/05/17.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImagesItemHolder> {
    private ImagesListCallback mCallback;
    private ArrayList<ImageItem> mImageList;

    public ImagesAdapter(ArrayList<ImageItem> imageList, ImagesListCallback callback) {
        mCallback = callback;
        mImageList = imageList;
    }

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String v) {
        Picasso.with(imageView.getContext()).load(v).error(R.drawable.question_mark)
                .placeholder(R.drawable.question_mark).into(imageView);
    }

    @Override
    public ImagesItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageElementBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.image_element, parent, false);
        binding.setCallback(mCallback);
        return new ImagesItemHolder(binding);
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
        ImageElementBinding mBinding;

        public ImagesItemHolder(ImageElementBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public void updateView(final ImageItem item) {
            mBinding.setImage(item);
        }

    }
}
