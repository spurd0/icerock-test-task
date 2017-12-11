package com.icerockdev.babenko.ui.images;

import android.content.Context;

import com.icerockdev.babenko.R;
import com.icerockdev.babenko.model.entities.ImageItem;
import com.icerockdev.babenko.repo.ImageRepository;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public class ImagesInteractorImpl implements ImagesInteractor {
    private static final String TAG = ImagesInteractorImpl.class.getName();
    private final Context mContext;
    private final ImageRepository mImageRepository;

    public ImagesInteractorImpl(ImageRepository imageRepository, Context context) {
        mImageRepository = imageRepository;
        mContext = context;
    }

    public Single<List<ImageItem>> requestPicturesList() {
        return mImageRepository
                .requestImages(mContext.getString(R.string.request_images_url))
                .map(Arrays::asList);

    }

}
