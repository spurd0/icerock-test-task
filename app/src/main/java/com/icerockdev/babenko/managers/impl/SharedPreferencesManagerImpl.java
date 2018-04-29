package com.icerockdev.babenko.managers.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.icerockdev.babenko.managers.SharedPreferencesManager;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public class SharedPreferencesManagerImpl implements SharedPreferencesManager {
    private static final String ERROR_CODE_KEY = "SharedPreferencesManagerImpl.ERROR_CODE_KEY";
    private static final String IS_USER_LOGGED_KEY = "SharedPreferencesManagerImpl.IS_USER_LOGGED_KEY";
    private SharedPreferences mManager;

    public SharedPreferencesManagerImpl(Context context) {
        mManager = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public int getErrorCode() {
        return mManager.getInt(ERROR_CODE_KEY, 0);
    }

    @Override
    public void saveErrorCode(int code) {
        mManager.edit().putInt(ERROR_CODE_KEY, code).apply();
    }

    @Override
    public boolean isUserLogged() {
        return mManager.getBoolean(IS_USER_LOGGED_KEY, false);
    }

    @Override
    public void setUserLogged(boolean logged) {
        mManager.edit().putBoolean(IS_USER_LOGGED_KEY, logged).apply();
    }


}
