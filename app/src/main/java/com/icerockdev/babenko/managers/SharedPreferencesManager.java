package com.icerockdev.babenko.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.interfaces.SharedPreferencesApi;
import com.icerockdev.babenko.utils.UtilsHelper;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public class SharedPreferencesManager implements SharedPreferencesApi{
    public static final String SERVER_ERROR_DIALOG_MESSAGE_KEY = "com.icerockdev.babenko.managers.HomeManager.SERVER_ERROR_DIALOG_MESSAGE_KEY";


    private SharedPreferences mManager;

    public SharedPreferencesManager(Context context) {
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
