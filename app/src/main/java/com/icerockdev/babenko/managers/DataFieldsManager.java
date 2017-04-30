package com.icerockdev.babenko.managers;

import android.util.Log;

import com.icerockdev.babenko.BuildConfig;
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
    private static DataFieldsManager sInstance;

    public static DataFieldsManager getInstance() {
        if (sInstance != null)
            return sInstance;
        sInstance = new DataFieldsManager();
        return sInstance;
    }

    public void requestDataFields(String Url, final RequestDataFieldsInterface callback){
        if (BuildConfig.DEBUG)
            Url = "http://www.mocky.io/v2/58fa10ce110000b81ad2106c"; // TODO: 30/04/17 move to unit test
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NetworkManager service = retrofit.create(NetworkManager.class);
        Call<DataField[]> data = service.requestDataFields(Url);
        data.enqueue(new Callback<DataField[]>() {
                @Override
                public void onResponse(Call<DataField[]> call, Response<DataField[]> response) {
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, response.body()[0].toString());
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
        void successfulResponse(String data);
        void errorResponse(String error);
    }
}
