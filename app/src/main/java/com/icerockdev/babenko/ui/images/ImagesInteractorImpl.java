package com.icerockdev.babenko.ui.images;

import com.icerockdev.babenko.model.entities.ImageItem;
import com.icerockdev.babenko.repo.ImageRepository;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Single;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public class ImagesInteractorImpl implements ImagesInteractor {
    private static final String TAG = "ImagesInteractorImpl";
    private final ImageRepository mImageRepository;

    public ImagesInteractorImpl(ImageRepository imageRepository) {
        mImageRepository = imageRepository;
    }

    public Single<List<ImageItem>> requestPicturesList() {
        return mImageRepository
                .requestImages()
                .map(Arrays::asList);

    }

}
