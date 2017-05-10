package com.icerockdev.babenko.managers;

import com.icerockdev.babenko.interfaces.NetworkApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Roman Babenko on 30/04/17.
 */

public class RetrofitManager {
    private NetworkApi mService;

    public RetrofitManager() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(NetworkApi.class);
    }

    public NetworkApi getService() {
        return mService;
    }
}
