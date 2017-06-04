package com.icerockdev.babenko.presenters;

import android.util.Log;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.interfaces.ImagesView;
import com.icerockdev.babenko.managers.impl.ImagesManagerImpl;
import com.icerockdev.babenko.managers.interfaces.ImagesManager;
import com.icerockdev.babenko.model.ImageItem;

import java.util.ArrayList;

import static com.icerockdev.babenko.managers.impl.ImagesManagerImpl.CODE_ERROR_LIST_NULL_RESPONSE;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public class ImagesPresenter extends BasePresenter<ImagesView> {
    private static final String TAG = "ImagesPresenter";
    private ImagesManager mManager;

    public ImagesPresenter(ImagesManager manager) {
        mManager = manager;
    }

    @Override
    public void attachView(ImagesView imagesView) {
        super.attachView(imagesView);
        requestPictures();
    }

    private void requestPictures() {
        getView().showProgressDialog();
        mManager.requestPicturesList(new ImagesManagerImpl.ImagesCallback() {
            @Override
            public void successResponse(ArrayList<ImageItem> images) {
                if (getView() != null) {
                    getView().dismissProgressDialog();
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "Images list length is " + images.size());
                    if (images.size() == 0) {
                        getView().showListIsEmptyError();
                        return;
                    }
                    getView().showImagesList(images);
                }
            }

            @Override
            public void failedResponse(int errorCode) {
                if (getView() != null) {
                    getView().dismissProgressDialog();
                    getView().showErrorDialog(errorCode);
                }
            }
        });
    }

}
