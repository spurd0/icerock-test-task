package com.icerockdev.babenko.managers;

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

    public void requestDataFields(String Url, final RequestDataFieldsInterface callback) {
        if (BuildConfig.DEBUG)
            Url = "http://www.mocky.io/v2/58fa10ce110000b81ad2106c"; // TODO: 30/04/17 move to unit test

        Call<DataField[]> data = IceRockApplication.getInstance().getRetrofitManager()
                .getService().requestDataFields(Url);
        data.enqueue(new Callback<DataField[]>() {
            @Override
            public void onResponse(Call<DataField[]> call, Response<DataField[]> response) {
                if (response.body() == null)
                    callback.errorResponse(IceRockApplication.getInstance()
                            .getString(R.string.request_data_fields_error_list_empty));
                callback.successfulResponse(response.body());
            }

            @Override
            public void onFailure(Call<DataField[]> call, Throwable t) {
                callback.errorResponse(t.getLocalizedMessage());
                if (BuildConfig.DEBUG)
                    Log.e(TAG, "Request error " + t.getLocalizedMessage());
            }
        });
    }

    public interface RequestDataFieldsInterface {
        void successfulResponse(DataField[] data);

        void errorResponse(String error);
    }
}
