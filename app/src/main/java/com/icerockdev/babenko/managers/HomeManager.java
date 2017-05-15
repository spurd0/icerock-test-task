package com.icerockdev.babenko.managers;

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
 * Created by Roman Babenko on 14/05/17.
 */

public class HomeManager {
    private static final String TAG = "HomeManager";

    public void requestDataFields(String url, final DataFieldsCallback callback) {
        final Call<DataFieldResponse[]> data = IceRockApplication.getInstance().getRetrofitManager()
                .getService().requestDataFields(url);
        data.enqueue(new Callback<DataFieldResponse[]>() {
            @Override
            public void onResponse(Call<DataFieldResponse[]> call, Response<DataFieldResponse[]> response) {
                if (response.body() == null) {
                    callback.failedResponse(IceRockApplication.getInstance()
                            .getString(R.string.request_data_fields_error_null));
                } else callback.successResponse(convertDataFields(response.body()));
            }

            @Override
            public void onFailure(Call<DataFieldResponse[]> call, Throwable t) {
                callback.failedResponse(t.getLocalizedMessage());
                if (BuildConfig.DEBUG)
                    Log.e(TAG, "Request error " + t.getLocalizedMessage());
            }
        });
    }

    private DataField[] convertDataFields(DataFieldResponse[] data) {
        DataField[] convertedData = new DataField[data.length];
        for (int i = 0; i < data.length; i++)
            convertedData[i] = new DataField(data[i].getId(), data[i].getType(),
                    data[i].getPlaceholder(),
                    data[i].getDefaultValue());
        return convertedData;
    }

    public interface DataFieldsCallback {
        void failedResponse(String error);

        void successResponse(DataField[] response);
    }
}
