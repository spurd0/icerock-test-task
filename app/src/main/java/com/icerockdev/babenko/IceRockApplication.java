package com.icerockdev.babenko;

import android.app.Application;

import com.icerockdev.babenko.managers.RetrofitManager;

/**
 * Created by Roman Babenko on 30/04/17.
 */

public class IceRockApplication extends Application {
    private static IceRockApplication sInstance;
    private RetrofitManager mRetrofitManager;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static IceRockApplication getInstance() {
        return sInstance;
    }

    public RetrofitManager getRetrofitManager() {
        if (mRetrofitManager != null)
            return mRetrofitManager;
        mRetrofitManager = new RetrofitManager();
        return mRetrofitManager;
    }

}
