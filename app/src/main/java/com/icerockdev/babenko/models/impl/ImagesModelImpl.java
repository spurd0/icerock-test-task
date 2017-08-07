package com.icerockdev.babenko.models.impl;

import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.models.interfaces.ImagesModel;
import com.icerockdev.babenko.model.ImageItem;
import com.icerockdev.babenko.services.ImageService;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public class ImagesModelImpl implements ImagesModel {
    public static final int CODE_ERROR_LIST_NULL_RESPONSE = 1;
    public static final int CODE_ERROR_OTHER = 2;
    private static final String TAG = "ImagesModelImpl";
    @Inject
    ImageService mImageService;
    private ArrayList<ImageItem> mImagesList;

    public ImagesModelImpl() {
        IceRockApplication.getAppComponent().inject(this);
    }

    public void requestPicturesList(final ImagesCallback callback) {
        mImageService.requestImages()
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(images -> {
                            ArrayList list = new ArrayList<>(Arrays.asList(images));
                            callback.successResponse(list);
                        },
                        exception -> callback.failedResponse(CODE_ERROR_OTHER));

    }

}
