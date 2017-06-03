package com.icerockdev.babenko.managers.impl;

import android.util.Log;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.managers.interfaces.HomeManager;
import com.icerockdev.babenko.model.DataFieldResponse;
import com.icerockdev.babenko.utils.UtilsHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Roman Babenko on 14/05/17.
 */

public class HomeManagerImpl implements HomeManager {
    private static final String TAG = "HomeManagerImpl";

    public void requestDataFields(String url, final DataFieldsCallback callback) {
        final Call<DataFieldResponse[]> data = IceRockApplication.getInstance().getRetrofitManager()
                .getService().requestDataFields(url);
        data.enqueue(new Callback<DataFieldResponse[]>() {
            @Override
            public void onResponse(Call<DataFieldResponse[]> call, Response<DataFieldResponse[]> response) {
                if (response.body() == null) {
                    callback.failedResponse(IceRockApplication.getInstance()
                            .getString(R.string.request_data_fields_error_null));
                } else callback.successResponse(UtilsHelper.convertDataFields(response.body())); // TODO: 02/06/17 get as list
            }

            @Override
            public void onFailure(Call<DataFieldResponse[]> call, Throwable t) {
                callback.failedResponse(t.getLocalizedMessage());
                if (BuildConfig.DEBUG)
                    Log.e(TAG, "Request error " + t.getLocalizedMessage());
            }
        });
    }

}
