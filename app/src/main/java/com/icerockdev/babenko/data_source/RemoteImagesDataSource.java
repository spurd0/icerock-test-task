package com.icerockdev.babenko.data_source;

import com.icerockdev.babenko.model.entities.ImageItem;

import io.reactivex.Single;

public interface RemoteImagesDataSource {
    Single<ImageItem[]> requestImages();
}
