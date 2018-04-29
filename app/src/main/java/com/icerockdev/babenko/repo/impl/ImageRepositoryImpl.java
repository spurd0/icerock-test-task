package com.icerockdev.babenko.repo.impl;

import com.icerockdev.babenko.data_source.RemoteImagesDataSource;
import com.icerockdev.babenko.model.entities.ImageItem;
import com.icerockdev.babenko.repo.ImageRepository;

import io.reactivex.Single;


/**
 * Created by Roman Babenko on 29/07/17.
 */

public class ImageRepositoryImpl implements ImageRepository {
    private final RemoteImagesDataSource remoteDataSource;

    public ImageRepositoryImpl(RemoteImagesDataSource remoteDataSource) {
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public Single<ImageItem[]> requestImages() {
        return remoteDataSource.requestImages();
    }
}
