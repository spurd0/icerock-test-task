package com.icerockdev.babenko.managers.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.icerockdev.babenko.managers.interfaces.SharedPreferencesManager;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public class SharedPreferencesManagerImpl implements SharedPreferencesManager {
    public static final String SERVER_ERROR_DIALOG_MESSAGE_KEY = "com.icerockdev.babenko.managers.impl.HomeManagerImpl.SERVER_ERROR_DIALOG_MESSAGE_KEY";


    private SharedPreferences mManager;

    public SharedPreferencesManagerImpl(Context context) {
        mManager = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public String getErrorMessage() {
        return mManager.getString(SERVER_ERROR_DIALOG_MESSAGE_KEY, "");
    }

    @Override
    public void saveErrorMessage(String message) {
        mManager.edit().putString(SERVER_ERROR_DIALOG_MESSAGE_KEY, message).apply();
    }


}
