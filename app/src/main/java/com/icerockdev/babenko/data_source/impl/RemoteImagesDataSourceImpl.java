package com.icerockdev.babenko.data_source.impl;

import com.icerockdev.babenko.core.NetworkApi;
import com.icerockdev.babenko.data_source.RemoteImagesDataSource;
import com.icerockdev.babenko.model.entities.ImageItem;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;

public class RemoteImagesDataSourceImpl implements RemoteImagesDataSource {
    private static final long REQUEST_IMAGES_TIMEOUT_SEC = 5;
    private final NetworkApi networkApi;
    private final String imagesUrl;

    public RemoteImagesDataSourceImpl(NetworkApi networkApi, String imagesUrl) {
        this.networkApi = networkApi;
        this.imagesUrl = imagesUrl;
    }

    @Override
    public Single<ImageItem[]> requestImages() {
        return networkApi
                .requestImages(imagesUrl)
                .timeout(REQUEST_IMAGES_TIMEOUT_SEC, TimeUnit.SECONDS);
    }
}
