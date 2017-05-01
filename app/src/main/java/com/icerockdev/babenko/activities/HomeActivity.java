package com.icerockdev.babenko.activities;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.data.ApplicationConstants;
import com.icerockdev.babenko.data.RequestStateMessage;
import com.icerockdev.babenko.fragments.ProgressDialogFragment;
import com.icerockdev.babenko.fragments.ServerErrorDialogFragment;
import com.icerockdev.babenko.managers.DataFieldsManager;
import com.icerockdev.babenko.model.DataField;

import static com.icerockdev.babenko.fragments.ServerErrorDialogFragment.DIALOG_MESSAGE_KEY;
import static com.icerockdev.babenko.managers.DataFieldsManager.RESPONSE_VALUE_KEY;

public class HomeActivity extends AppCompatActivity {
    private static final String SERVER_ERROR_DIALOG_TAG = "com.icerockdev.babenko.activities.SERVER_ERROR_DIALOG_TAG";
    private static final String PROGRESS_DIALOG_TAG = "com.icerockdev.babenko.activities.PROGRESS_DIALOG_TAG";
    private static final String SERVER_ERROR_DIALOG_MESSAGE_KEY = "com.icerockdev.babenko.activities.SERVER_ERROR_DIALOG_MESSAGE_KEY";
    private static final String TAG = "HomeActivity";
    private EditText mRequestUrlEditText;
    private boolean mActivityPaused;
    private BroadcastReceiver mRequestFieldsReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
    }

    private void initViews() {
        mRequestUrlEditText = (EditText) findViewById(R.id.fieldsRequestUrlEditText);
        Button requestFieldsButton = (Button) findViewById(R.id.getFieldsButton);
        mRequestUrlEditText.setText(ApplicationConstants.URL_START);
        mRequestUrlEditText.setSelection(mRequestUrlEditText.getText().length());
        requestFieldsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDataFields();
            }
        });
    }

    private void requestDataFields() {
        String url = mRequestUrlEditText.getText().toString();
        if (!URLUtil.isValidUrl(url)) {
            mRequestUrlEditText.setError(getString(R.string.url_error));
            return;
        }
        ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();
        progressDialogFragment.show(getSupportFragmentManager(), PROGRESS_DIALOG_TAG);
        IceRockApplication.getInstance().getDataFieldsManager().requestDataFields(url);
    }

    private void showErrorDialog(String error) {
        ServerErrorDialogFragment serverErrorDialogFragment = new ServerErrorDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putString(DIALOG_MESSAGE_KEY, error);
        serverErrorDialogFragment.setArguments(arguments);
        //app shows that it`s not finishing but it will crash if i exit app here, i`m sorry =\
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && this.isDestroyed()) || this.isFinishing() || mActivityPaused) {
            needToShowDialogAfterResume(error);
        } else try {
            serverErrorDialogFragment.show(getSupportFragmentManager(), SERVER_ERROR_DIALOG_TAG);
        } catch (IllegalStateException ex) {
            if (BuildConfig.DEBUG)
                ex.printStackTrace();
            needToShowDialogAfterResume(error);
        }
    }

    @Override
    protected void onPause() {
        mActivityPaused = true;
        super.onPause();
    }

    private void needToShowDialogAfterResume(String error) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this); // i don`t trust saveinstancestate
        prefs.edit().putString(SERVER_ERROR_DIALOG_MESSAGE_KEY, error).apply();
    }

    private void gotDataFields(DataField[] data) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "Data field count is " + data.length);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerRequestFieldsReceiver();
        mActivityPaused = false;
        checkForRequestProgress();
    }

    @Override
    protected void onDestroy() {
        unregisterRequestFieldsReceiver();
        super.onDestroy();
    }

    private void checkForRequestProgress() {
        RequestStateMessage message = IceRockApplication.getInstance().getDataFieldsManager().getStateMessage();
        if (message == null || message.isFinished()) {
            checkAndDismissProgressDialog();
            checkForErrors();
        } else {
            ProgressDialogFragment progressDialogFragment = (ProgressDialogFragment) getSupportFragmentManager().
                    findFragmentByTag(PROGRESS_DIALOG_TAG);
            if (progressDialogFragment == null) {
                progressDialogFragment = new ProgressDialogFragment();
                progressDialogFragment.show(getSupportFragmentManager(), PROGRESS_DIALOG_TAG);
            }
        }
    }

    private void checkForErrors() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String dialogErrorMessage = prefs.getString(SERVER_ERROR_DIALOG_MESSAGE_KEY, "");
        if (!dialogErrorMessage.isEmpty()) {
            prefs.edit().putString(SERVER_ERROR_DIALOG_MESSAGE_KEY, "").apply();
            showErrorDialog(dialogErrorMessage);
        }
    }

    private void registerRequestFieldsReceiver() {
        mRequestFieldsReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                checkAndDismissProgressDialog();
                RequestStateMessage message = intent.getParcelableExtra(RESPONSE_VALUE_KEY);
                if (message.isSuccess())
                    gotDataFields(message.getDataFields());
                else showErrorDialog(message.getErrorMessage());
            }
        };
        this.registerReceiver(mRequestFieldsReceiver, new IntentFilter(DataFieldsManager.RESPONSE_ACTION));
    }

    private void unregisterRequestFieldsReceiver() {
        if (mRequestFieldsReceiver != null)
            this.unregisterReceiver(mRequestFieldsReceiver);
    }

    private void checkAndDismissProgressDialog() {
        ProgressDialogFragment progressDialogFragment = (ProgressDialogFragment) getSupportFragmentManager().
                findFragmentByTag(PROGRESS_DIALOG_TAG);
        if (progressDialogFragment != null)
            progressDialogFragment.dismiss();
    }
}
