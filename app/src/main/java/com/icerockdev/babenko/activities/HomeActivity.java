package com.icerockdev.babenko.activities;

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
import android.widget.EditText;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.data.ApplicationConstants;
import com.icerockdev.babenko.interfaces.HomeView;
import com.icerockdev.babenko.model.RequestStateMessage;
import com.icerockdev.babenko.fragments.ProgressDialogFragment;
import com.icerockdev.babenko.fragments.ServerErrorDialogFragment;
import com.icerockdev.babenko.managers.DataFieldsManager;
import com.icerockdev.babenko.model.DataField;
import com.icerockdev.babenko.presenters.HomePresenter;

import static com.icerockdev.babenko.activities.DataFieldsActivity.DATA_FIELDS_KEY;
import static com.icerockdev.babenko.fragments.ServerErrorDialogFragment.DIALOG_MESSAGE_KEY;
import static com.icerockdev.babenko.managers.DataFieldsManager.SERVER_ERROR_DIALOG_MESSAGE_KEY;

public class HomeActivity extends AppCompatActivity implements HomeView{
    private static final String SERVER_ERROR_DIALOG_TAG = "com.icerockdev.babenko.activities.SERVER_ERROR_DIALOG_TAG";
    private static final String PROGRESS_DIALOG_TAG = "com.icerockdev.babenko.activities.PROGRESS_DIALOG_TAG";
    private static final String TAG = "HomeActivity";
    private EditText mRequestUrlEditText;
    private boolean mActivityPaused;
    private HomePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initViews();
    }

    private void initViews() {
        mRequestUrlEditText = (EditText) findViewById(R.id.fieldsRequestUrlEditText);
        mRequestUrlEditText.setText(ApplicationConstants.URL_START);
        mRequestUrlEditText.setSelection(mRequestUrlEditText.getText().length());
    }

    public void requestDataFieldsButtonClicked(View v) {

    }

    private void showErrorDialog(String error) {
        ServerErrorDialogFragment serverErrorDialogFragment = new ServerErrorDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putString(DIALOG_MESSAGE_KEY, error);
        serverErrorDialogFragment.setArguments(arguments);
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && this.isDestroyed()) || this.isFinishing() || mActivityPaused) {
            return;
        } else try {
            serverErrorDialogFragment.show(getSupportFragmentManager(), SERVER_ERROR_DIALOG_TAG);
        } catch (IllegalStateException ex) {
            if (BuildConfig.DEBUG)
                ex.printStackTrace();
            return;
        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.edit().putString(SERVER_ERROR_DIALOG_MESSAGE_KEY, "").apply();
    }

    @Override
    protected void onPause() {
        mActivityPaused = true;
        super.onPause();
    }

    public void gotDataFields(DataField[] data) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "Data field count is " + data.length);
        Intent dataFieldsIntent = new Intent(this, DataFieldsActivity.class);
        dataFieldsIntent.putExtra(DATA_FIELDS_KEY, data);
        startActivity(dataFieldsIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mActivityPaused = false;
        checkForRequestProgress();
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
        if (!dialogErrorMessage.isEmpty())
            showErrorDialog(dialogErrorMessage);
    }

    private void checkAndDismissProgressDialog() {
        if (this.isFinishing())
            return;
        ProgressDialogFragment progressDialogFragment = (ProgressDialogFragment) getSupportFragmentManager().
                findFragmentByTag(PROGRESS_DIALOG_TAG);
        if (progressDialogFragment != null)
            progressDialogFragment.dismiss();
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public void showProgressDialog() {
        ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();
        progressDialogFragment.show(getSupportFragmentManager(), PROGRESS_DIALOG_TAG);
    }

    @Override
    public void dismissProgressDialog() {

    }

    @Override
    public void showErrorMessage(String message) {

    }

    @Override
    public void showUrlError(String error) {
        mRequestUrlEditText.setError(error);
    }
}
