package com.icerockdev.babenko.managers.impl;

import android.util.Log;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.managers.interfaces.HomeManager;
import com.icerockdev.babenko.managers.interfaces.RetrofitManager;
import com.icerockdev.babenko.model.DataFieldResponse;
import com.icerockdev.babenko.utils.UtilsHelper;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Roman Babenko on 14/05/17.
 */

public class HomeManagerImpl implements HomeManager {
    public static final int ERROR_CODE_RESPONSE_NULL = 1;
    public static final int ERROR_CODE_RESPONSE_OTHER = 2;
    private static final String TAG = "HomeManagerImpl";

    @Inject
    RetrofitManager mRetrofitManager;

    public HomeManagerImpl() {
        IceRockApplication.getAppComponent().inject(this);
    }

    public void requestDataFields(String url, final DataFieldsCallback callback) {
        final Call<DataFieldResponse[]> data = mRetrofitManager
                .getService().requestDataFields(url);
        data.enqueue(new Callback<DataFieldResponse[]>() {
            @Override
            public void onResponse(Call<DataFieldResponse[]> call, Response<DataFieldResponse[]> response) {
                if (response.body() == null) {
                    callback.failedResponse(ERROR_CODE_RESPONSE_NULL);
                } else
                    callback.successResponse(UtilsHelper.convertDataFields(response.body())); // TODO: 02/06/17 get as list
            }

            @Override
            public void onFailure(Call<DataFieldResponse[]> call, Throwable t) {
                callback.failedResponse(ERROR_CODE_RESPONSE_OTHER);
                if (BuildConfig.DEBUG)
                    Log.e(TAG, "Request error " + t.getLocalizedMessage());
            }
        });
    }

}
