package com.icerockdev.babenko.managers.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.icerockdev.babenko.managers.SharedPreferencesManager;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public class SharedPreferencesManagerImpl implements SharedPreferencesManager {
    private SharedPreferences mManager;

    public SharedPreferencesManagerImpl(Context context) {
        mManager = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public Integer getErrorCode(String key) {
        return mManager.getInt(key, 0);
    }

    @Override
    public void saveErrorCode(String key, int code) {
        mManager.edit().putInt(key, code).apply();
    }


}
