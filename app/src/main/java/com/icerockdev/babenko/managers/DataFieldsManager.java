package com.icerockdev.babenko.managers;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.model.DataField;
import com.icerockdev.babenko.model.DataFieldResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Roman Babenko on 30/04/17.
 */

public class DataFieldsManager {
    private static final String TAG = "DataFieldsManager";
    public static final String SERVER_ERROR_DIALOG_MESSAGE_KEY = "com.icerockdev.babenko.managers.DataFieldsManager.SERVER_ERROR_DIALOG_MESSAGE_KEY";

    public void requestDataFields(String Url, final DataFieldsCallback callback) {
        if (BuildConfig.DEBUG)
            Url = "http://www.mocky.io/v2/58fa10ce110000b81ad2106c"; // TODO: 30/04/17 move to unit test

        final Call<DataFieldResponse[]> data = IceRockApplication.getInstance().getRetrofitManager()
                .getService().requestDataFields(Url);
        data.enqueue(new Callback<DataFieldResponse[]>() {
            @Override
            public void onResponse(Call<DataFieldResponse[]> call, Response<DataFieldResponse[]> response) {
                if (response.body() == null) {
                    callback.failedResponse(IceRockApplication.getInstance()
                            .getString(R.string.request_data_fields_error_null));
                } else callback.successResponse(response.body());
            }

            @Override
            public void onFailure(Call<DataFieldResponse[]> call, Throwable t) {
                callback.failedResponse(t.getLocalizedMessage());
                if (BuildConfig.DEBUG)
                    Log.e(TAG, "Request error " + t.getLocalizedMessage());
            }
        });
    }

    public interface DataFieldsCallback {
        void failedResponse(String error);

        void successResponse(DataFieldResponse[] response);
    }
}
