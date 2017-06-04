package com.icerockdev.babenko.managers.impl;

import com.icerockdev.babenko.interfaces.NetworkApi;
import com.icerockdev.babenko.managers.interfaces.RetrofitManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Roman Babenko on 30/04/17.
 */

public class RetrofitManagerImpl implements RetrofitManager {
    private NetworkApi mService;

    public RetrofitManagerImpl() {
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
