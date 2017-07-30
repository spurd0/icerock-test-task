package com.icerockdev.babenko.ui.images;

import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.managers.impl.ImagesManagerImpl;
import com.icerockdev.babenko.managers.interfaces.ImagesManager;
import com.icerockdev.babenko.model.ImageItem;
import com.icerockdev.babenko.ui.BasePresenter;

import java.util.ArrayList;

/**
 * Created by Roman Babenko on 10/05/17.
 */
@InjectViewState
public class ImagesPresenter extends BasePresenter<ImagesView> {
    private static final String TAG = "ImagesPresenter";
    private ImagesManager mManager;

    public ImagesPresenter(ImagesManager manager) {
        mManager = manager;
    }

    @Override
    public void onFirstViewAttach() {
        super.onFirstViewAttach();
        requestPictures();
    }

    private void requestPictures() {
        getViewState().showProgressDialog();
        mManager.requestPicturesList(new ImagesManagerImpl.ImagesCallback() {
            @Override
            public void successResponse(ArrayList<ImageItem> images) {
                if (getViewState() != null) {
                    getViewState().dismissProgressDialog();
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "Images list length is " + images.size());
                    if (images.size() == 0) {
                        getViewState().showListIsEmptyError();
                        return;
                    }
                    getViewState().showImagesList(images);
                }
            }

            @Override
            public void failedResponse(int errorCode) {
                if (getViewState() != null) {
                    getViewState().dismissProgressDialog();
                    getViewState().showErrorDialog(errorCode);
                }
            }
        });
    }

}
