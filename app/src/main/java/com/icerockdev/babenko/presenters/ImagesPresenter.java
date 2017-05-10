package com.icerockdev.babenko.presenters;

import android.os.Parcelable;
import android.util.Log;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.interfaces.ImagesView;
import com.icerockdev.babenko.managers.ImagesManager;
import com.icerockdev.babenko.model.DataField;
import com.icerockdev.babenko.model.ImageResponse;

import java.util.ArrayList;
import java.util.Collections;

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
                        Log.d(TAG, "Images list length is " + images.length);
                    if (images.length == 0) {
                        getView().showErrorDialog("List is empty");
                        return;
                    }
                    ArrayList<ImageResponse> imagesList = new ArrayList<>();
                    Collections.addAll(imagesList, images);
                    getView().gotImagesList(imagesList);
                } else {
                    // TODO: 10/05/17 record response
                }

            }

            @Override
            public void failedResponse(String error) {
                if (getView() != null) {
                    getView().showErrorDialog(error);
                } else {
                    // TODO: 10/05/17 record error
                }
            }
        });
    }


}
