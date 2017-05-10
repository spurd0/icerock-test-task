package com.icerockdev.babenko.presenters;

import android.util.Log;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.interfaces.ImagesView;
import com.icerockdev.babenko.managers.ImagesManager;
import com.icerockdev.babenko.model.ImageResponse;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public class ImagesPresenter extends BasePresenter<ImagesView> {
    private static final String TAG = "ImagesPresenter";

    public void requestPictures() {
        IceRockApplication.getInstance().getImagesManager().requestPicturesList(new ImagesManager.ImagesCallback() {
            @Override
            public void successResponse(ImageResponse[] images) {
                if (getView() != null) {
                    if (BuildConfig.DEBUG)
                        Log.e(TAG, "Images list length is " + images.length);
                } else {

                }

            }

            @Override
            public void failedResponse(String error) {
                if (getView() != null) {

                } else {

                }
            }
        });
    }
}
