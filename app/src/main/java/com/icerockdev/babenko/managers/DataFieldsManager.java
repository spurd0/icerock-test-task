package com.icerockdev.babenko.managers;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.model.RequestStateMessage;
import com.icerockdev.babenko.model.DataField;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Roman Babenko on 30/04/17.
 */

public class DataFieldsManager {
    private static final String TAG = "DataFieldsManager";
    public static final String SERVER_ERROR_DIALOG_MESSAGE_KEY = "com.icerockdev.babenko.managers.DataFieldsManager.SERVER_ERROR_DIALOG_MESSAGE_KEY";
    public static final String RESPONSE_ACTION = "com.icerockdev.babenko.managers.DataFieldsManager.RESPONSE_ACTION";
    public static final String RESPONSE_VALUE_KEY = "com.icerockdev.babenko.managers.DataFieldsManager.RESPONSE_VALUE_KEY";
    private RequestStateMessage mStateMessage;

    public void requestDataFields(String Url) {
        if (BuildConfig.DEBUG)
            Url = "http://www.mocky.io/v2/58fa10ce110000b81ad2106c"; // TODO: 30/04/17 move to unit test

        final Call<DataField[]> data = IceRockApplication.getInstance().getRetrofitManager()
                .getService().requestDataFields(Url);
        mStateMessage = new RequestStateMessage();
        mStateMessage.setFinished(false);
        data.enqueue(new Callback<DataField[]>() {
            @Override
            public void onResponse(Call<DataField[]> call, Response<DataField[]> response) {
                mStateMessage.setFinished(true);
                Intent intent = new Intent(RESPONSE_ACTION);
                if (response.body() == null) {
                    mStateMessage.setSuccess(false);
                    mStateMessage.setErrorMessage(IceRockApplication.getInstance()
                            .getString(R.string.request_data_fields_error_list_empty));
                    saveError(IceRockApplication.getInstance()
                            .getString(R.string.request_data_fields_error_list_empty));
                } else {
                    mStateMessage.setSuccess(true);
                    mStateMessage.setDataFields(response.body());

                }
                intent.putExtra(RESPONSE_VALUE_KEY, mStateMessage);
                IceRockApplication.getInstance().sendBroadcast(intent);
            }

            @Override
            public void onFailure(Call<DataField[]> call, Throwable t) {
                mStateMessage.setFinished(true);
                mStateMessage.setSuccess(false);
                mStateMessage.setErrorMessage(t.getLocalizedMessage());
                saveError(t.getLocalizedMessage());
                Intent intent = new Intent(RESPONSE_ACTION);
                intent.putExtra(RESPONSE_VALUE_KEY, mStateMessage);
                IceRockApplication.getInstance().sendBroadcast(intent);
                if (BuildConfig.DEBUG)
                    Log.e(TAG, "Request error " + t.getLocalizedMessage());
            }
        });
    }

    private void saveError(String error) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(IceRockApplication.getInstance());
        prefs.edit().putString(SERVER_ERROR_DIALOG_MESSAGE_KEY, error).apply();
    }

    public RequestStateMessage getStateMessage() {
        return mStateMessage;
    }
}
