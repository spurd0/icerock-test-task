package com.icerockdev.babenko.presenters;

import android.app.Activity;

import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.interfaces.FullScreenImageView;
import com.icerockdev.babenko.managers.ImagesManager;

/**
 * Created by Roman Babenko on 5/11/2017.
 */

public class FullScreenImagePresenter extends BasePresenter<FullScreenImageView> {
    public static final String IMAGE_URL_KEY = "com.icerockdev.babenko.presenters.FullScreenImagePresenter.IMAGE_URL_KEY";

    public void requestPicture(Activity activity) {
        // getView().showProgressDialog(); // TODO: 5/11/2017 same as in the homeactivity, think about it
        String imageUrl = getImageUrl(activity);
        if (imageUrl == null)
            throw new NullPointerException("Image url is null");
        IceRockApplication.getInstance().getImagesManager().requestPicture(getImageUrl(activity), new ImagesManager.ImageLoadingCallback() {
            @Override
            public void successResponse() {

            }

            @Override
            public void failedResponse() {

            }
        });
    }

    private String getImageUrl(Activity activity) {
        return activity.getIntent().getStringExtra(IMAGE_URL_KEY);
    }

}