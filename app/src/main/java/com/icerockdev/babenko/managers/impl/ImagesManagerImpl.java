package com.icerockdev.babenko.managers.impl;

import android.util.Log;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.managers.interfaces.ImagesManager;
import com.icerockdev.babenko.model.ImageItem;
import com.icerockdev.babenko.model.ImageResponse;
import com.icerockdev.babenko.utils.UtilsHelper;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.icerockdev.babenko.data.ApplicationConstants.REQUEST_IMAGES_URL;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public class ImagesManagerImpl implements ImagesManager {
    private static final String TAG = "ImagesManagerImpl";

    private ArrayList<ImageItem> mImagesList;

    public void requestPicturesList(final ImagesCallback callback) {
        if (mImagesList == null) {
            final Call<ImageResponse[]> data = IceRockApplication.getInstance().getRetrofitManager()
                    .getService().requestImages(REQUEST_IMAGES_URL);
            data.enqueue(new Callback<ImageResponse[]>() {
                @Override
                public void onResponse(Call<ImageResponse[]> call, Response<ImageResponse[]> response) {
                    if (response.body() == null) {
                        callback.failedResponse(IceRockApplication.getInstance()
                                .getString(R.string.request_data_fields_error_null));
                    } else {
                        mImagesList = UtilsHelper.convertImagesList(response.body());
                        callback.successResponse(mImagesList);
                    }
                }

                @Override
                public void onFailure(Call<ImageResponse[]> call, Throwable t) {
                    callback.failedResponse(t.getLocalizedMessage());
                    if (BuildConfig.DEBUG)
                        Log.e(TAG, "Request error " + t.getLocalizedMessage());
                }
            });
        } else {
            callback.successResponse(mImagesList);
        }

    }

}
