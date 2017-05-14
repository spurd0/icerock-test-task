package com.icerockdev.babenko.presenters;

import android.os.Parcelable;
import android.util.Log;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.interfaces.ImagesView;
import com.icerockdev.babenko.managers.ImagesManager;
import com.icerockdev.babenko.model.DataField;
import com.icerockdev.babenko.model.ImageItem;
import com.icerockdev.babenko.model.ImageResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public class ImagesPresenter extends BasePresenter<ImagesView> {
    private static final String TAG = "ImagesPresenter";

    public void requestPictures() {
        getView().showProgressDialog();
        IceRockApplication.getInstance().getImagesManager().requestPicturesList(new ImagesManager.ImagesCallback() {
            @Override
            public void successResponse(ImageResponse[] images) {
                if (getView() != null) {
                    getView().dismissProgressDialog();
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "Images list length is " + images.length);
                    if (images.length == 0) {
                        getView().showListIsEmptyError();
                        return;
                    }
                    getView().gotImagesList(convertImagesList(images));
                }
            }

            @Override
            public void failedResponse(String error) {
                if (getView() != null) {
                    getView().dismissProgressDialog();
                    getView().showErrorDialog(error);
                }
            }
        });
    }

    private ArrayList<ImageItem> convertImagesList(ImageResponse[] images) {
        ArrayList<ImageItem> result = new ArrayList<>();
        for (ImageResponse imageResponse : images) {
            result.add(new ImageItem(imageResponse));
        }
        return result;
    }


}
