package com.icerockdev.babenko.repo.impl;

import com.icerockdev.babenko.core.NetworkApi;
import com.icerockdev.babenko.model.entities.ImageItem;
import com.icerockdev.babenko.repo.ImageRepository;

import io.reactivex.Single;


/**
 * Created by Roman Babenko on 29/07/17.
 */

public class ImageRepositoryImpl implements ImageRepository {
    private NetworkApi mDataFieldsApi;

    public ImageRepositoryImpl(NetworkApi networkApi) {
        mDataFieldsApi = networkApi;
    }

    @Override
    public Single<ImageItem[]> requestImages(String imagesUrl) {
        return mDataFieldsApi.requestImages(imagesUrl);
    }
}
