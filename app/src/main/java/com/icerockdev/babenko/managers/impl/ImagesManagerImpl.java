package com.icerockdev.babenko.managers.impl;

import android.util.Log;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.managers.interfaces.ImagesManager;
import com.icerockdev.babenko.managers.interfaces.RetrofitManager;
import com.icerockdev.babenko.model.ImageItem;
import com.icerockdev.babenko.model.ImageResponse;
import com.icerockdev.babenko.utils.UtilsHelper;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.icerockdev.babenko.data.ApplicationConstants.REQUEST_IMAGES_URL;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public class ImagesManagerImpl implements ImagesManager {
    public static final int CODE_ERROR_LIST_NULL_RESPONSE = 1;
    public static final int CODE_ERROR_OTHER = 2;
    private static final String TAG = "ImagesManagerImpl";
    @Inject
    RetrofitManager mRetrofitManager;
    private ArrayList<ImageItem> mImagesList;

    public ImagesManagerImpl() {
        IceRockApplication.getAppComponent().inject(this);
    }

    public void requestPicturesList(final ImagesCallback callback) {
        if (mImagesList == null) {
            final Call<ImageResponse[]> data = mRetrofitManager
                    .getService().requestImages(REQUEST_IMAGES_URL);
            data.enqueue(new Callback<ImageResponse[]>() {
                @Override
                public void onResponse(Call<ImageResponse[]> call, Response<ImageResponse[]> response) {
                    if (response.body() == null) {
                        callback.failedResponse(CODE_ERROR_LIST_NULL_RESPONSE);
                    } else {
                        mImagesList = UtilsHelper.convertImagesList(response.body());
                        callback.successResponse(mImagesList);
                    }
                }

                @Override
                public void onFailure(Call<ImageResponse[]> call, Throwable t) {
                    callback.failedResponse(CODE_ERROR_OTHER);
                    if (BuildConfig.DEBUG)
                        Log.e(TAG, "Request error " + t.getLocalizedMessage());
                }
            });
        } else {
            callback.successResponse(mImagesList);
        }

    }

}
