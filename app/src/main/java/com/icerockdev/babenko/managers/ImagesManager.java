package com.icerockdev.babenko.managers;

import android.util.Log;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.model.ImageResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.icerockdev.babenko.data.ApplicationConstants.REUEST_IMAGES_URL;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public class ImagesManager {
    private static final String TAG = "ImagesManager";

    public void requestPicturesList(final ImagesCallback callback) {
        final Call<ImageResponse[]> data = IceRockApplication.getInstance().getRetrofitManager()
                .getService().requestImages(REUEST_IMAGES_URL);
        data.enqueue(new Callback<ImageResponse[]>() {
            @Override
            public void onResponse(Call<ImageResponse[]> call, Response<ImageResponse[]> response) {
                if (response.body() == null) {
                    callback.failedResponse(IceRockApplication.getInstance()
                            .getString(R.string.request_data_fields_error_null));
                } else callback.successResponse(response.body());
            }

            @Override
            public void onFailure(Call<ImageResponse[]> call, Throwable t) {
                callback.failedResponse(t.getLocalizedMessage());
                if (BuildConfig.DEBUG)
                    Log.e(TAG, "Request error " + t.getLocalizedMessage());
            }
        });
    }

    public interface ImagesCallback{
        void successResponse(ImageResponse[] images);
        void failedResponse(String error);
    }
}
