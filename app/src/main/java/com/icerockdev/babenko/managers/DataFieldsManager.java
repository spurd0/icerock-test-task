package com.icerockdev.babenko.managers;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.model.DataField;

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

        final Call<DataField[]> data = IceRockApplication.getInstance().getRetrofitManager()
                .getService().requestDataFields(Url);
        data.enqueue(new Callback<DataField[]>() {
            @Override
            public void onResponse(Call<DataField[]> call, Response<DataField[]> response) {
                if (response.body() == null) {
                    saveError(IceRockApplication.getInstance()
                            .getString(R.string.request_data_fields_error_list_empty));
                    callback.failedResponse("List is null"); // TODO: 06/05/17 move to string res // TODO: 06/05/17 list is null, remade
                } else {
                    callback.successResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<DataField[]> call, Throwable t) {
                saveError(t.getLocalizedMessage());
                callback.failedResponse(t.getLocalizedMessage());
                if (BuildConfig.DEBUG)
                    Log.e(TAG, "Request error " + t.getLocalizedMessage());
            }
        });
    }

    private void saveError(String error) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(IceRockApplication.getInstance());
        prefs.edit().putString(SERVER_ERROR_DIALOG_MESSAGE_KEY, error).apply();
    }

    public interface DataFieldsCallback {
        void failedResponse(String error);
        void successResponse(DataField[] response);
    }
}
