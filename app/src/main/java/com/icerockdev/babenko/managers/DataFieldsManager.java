package com.icerockdev.babenko.managers;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.model.DataField;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Roman Babenko on 30/04/17.
 */

public class DataFieldsManager {
    private static final String TAG = "DataFieldsManager";
    public static final String RESPONSE_ACTION = "com.icerockdev.babenko.managers.DataFieldsManager.RESPONSE_ACTION";
    public static final String RESPONSE_ERROR_KEY = "com.icerockdev.babenko.managers.DataFieldsManager.RESPONSE_ERROR_KEY";
    public static final String RESPONSE_VALUE_KEY = "com.icerockdev.babenko.managers.DataFieldsManager.RESPONSE_VALUE_KEY";

    public void requestDataFields(String Url) {
        if (BuildConfig.DEBUG)
            Url = "http://www.mocky.io/v2/58fa10ce110000b81ad2106c123"; // TODO: 30/04/17 move to unit test

        final Call<DataField[]> data = IceRockApplication.getInstance().getRetrofitManager()
                .getService().requestDataFields(Url);

        data.enqueue(new Callback<DataField[]>() {
            @Override
            public void onResponse(Call<DataField[]> call, Response<DataField[]> response) {
                Intent intent = new Intent(RESPONSE_ACTION);
                if (response.body() == null)
                    intent.putExtra(RESPONSE_ERROR_KEY, IceRockApplication.getInstance()
                            .getString(R.string.request_data_fields_error_list_empty));
                else intent.putExtra(RESPONSE_VALUE_KEY, response.body());

                IceRockApplication.getInstance().sendBroadcast(intent);
            }

            @Override
            public void onFailure(Call<DataField[]> call, Throwable t) {
                Intent intent = new Intent(RESPONSE_ACTION);
                intent.putExtra(RESPONSE_ERROR_KEY, t.getLocalizedMessage());
                IceRockApplication.getInstance().sendBroadcast(intent);
                if (BuildConfig.DEBUG)
                    Log.e(TAG, "Request error " + t.getLocalizedMessage());
            }

        });

    }
}
