package com.icerockdev.babenko.managers.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.managers.interfaces.SharedPreferencesManager;

import javax.inject.Inject;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public class SharedPreferencesManagerImpl implements SharedPreferencesManager {
    public static final String SERVER_ERROR_DIALOG_MESSAGE_KEY = "com.icerockdev.babenko.models.impl.HomeModelImpl.SERVER_ERROR_DIALOG_MESSAGE_KEY";
    @Inject
    Context mContext;

    private SharedPreferences mManager;

    public SharedPreferencesManagerImpl() {
        IceRockApplication.getAppComponent().inject(this);
        mManager = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    @Override
    public Integer getErrorCode() {
        return mManager.getInt(SERVER_ERROR_DIALOG_MESSAGE_KEY, 0);
    }

    @Override
    public void saveErrorCode(int code) {
        mManager.edit().putInt(SERVER_ERROR_DIALOG_MESSAGE_KEY, code).apply();
    }


}
