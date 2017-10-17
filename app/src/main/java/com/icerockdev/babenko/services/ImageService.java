package com.icerockdev.babenko.services;

import com.icerockdev.babenko.interfaces.NetworkApi;
import com.icerockdev.babenko.model.entities.ImageItem;

import io.reactivex.Observable;

import static com.icerockdev.babenko.core.ApplicationConstants.REQUEST_IMAGES_URL;

/**
 * Created by Roman Babenko on 29/07/17.
 */

public class ImageService {
    private NetworkApi mDataFieldsApi;

    public ImageService(NetworkApi networkApi) {
        mDataFieldsApi = networkApi;
    }

    public Observable<ImageItem[]> requestImages() {
        return mDataFieldsApi.requestImages(REQUEST_IMAGES_URL);
    }
}
