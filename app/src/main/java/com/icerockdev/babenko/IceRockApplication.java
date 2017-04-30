package com.icerockdev.babenko;

import android.app.Application;

import com.icerockdev.babenko.managers.DataFieldsManager;
import com.icerockdev.babenko.managers.RetrofitManager;

/**
 * Created by Roman Babenko on 30/04/17.
 */

public class IceRockApplication extends Application {
    private static IceRockApplication sInstance;
    private DataFieldsManager mDataFieldsManager;
    private RetrofitManager mRetrofitManager;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static IceRockApplication getInstance() {
        return sInstance;
    }

    public DataFieldsManager getDataFieldsManager() {
        if (mDataFieldsManager != null)
            return mDataFieldsManager;
        mDataFieldsManager = new DataFieldsManager();
        return mDataFieldsManager;
    }

    public RetrofitManager getRetrofitManager() {
        if (mRetrofitManager != null)
            return mRetrofitManager;
        mRetrofitManager = new RetrofitManager();
        return mRetrofitManager;
    }
}
