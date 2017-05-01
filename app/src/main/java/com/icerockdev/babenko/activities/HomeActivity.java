package com.icerockdev.babenko.activities;

import android.app.Dialog;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.data.ApplicationConstants;
import com.icerockdev.babenko.fragments.ServerErrorDialogFragment;
import com.icerockdev.babenko.managers.DataFieldsManager;
import com.icerockdev.babenko.model.DataField;
import com.icerockdev.babenko.utils.UtilsHelper;

import static com.icerockdev.babenko.fragments.ServerErrorDialogFragment.DIALOG_MESSAGE_KEY;

public class HomeActivity extends AppCompatActivity {
    private static final String SERVER_ERROR_DIALOG_TAG = "com.icerockdev.babenko.activities.SERVER_ERROR_DIALOG_TAG";
    private static final String SERVER_ERROR_DIALOG_MESSAGE_KEY = "com.icerockdev.babenko.activities.SERVER_ERROR_DIALOG_MESSAGE_KEY";
    private static final String SERVER_ERROR_DIALOG_NEED_TO_SHOW_KEY = "com.icerockdev.babenko.activities.SERVER_ERROR_DIALOG_NEED_TO_SHOW_KEY";
    private static final String TAG = "HomeActivity";
    private EditText mRequestUrlEditText;
    private boolean mNeedToShowErrorDialog;
    private String mDialogErrorMessage;
    private boolean mActivityPaused;

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
        final Dialog progressDialog = UtilsHelper.createProgressDialog(this);
        IceRockApplication.getInstance().getDataFieldsManager().requestDataFields(url, new DataFieldsManager.RequestDataFieldsInterface() {
            @Override
            public void successfulResponse(DataField[] data) {
                progressDialog.dismiss();
                gotDataFields(data);
            }

            @Override
            public void errorResponse(String error) {
                progressDialog.dismiss();
                showErrorDialog(error);
            }
        });
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
        mDialogErrorMessage = error;
        mNeedToShowErrorDialog = true;
    }

    private void gotDataFields(DataField[] data) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mActivityPaused = false;
        if (mNeedToShowErrorDialog) {
            mNeedToShowErrorDialog = false;
            showErrorDialog(mDialogErrorMessage);
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(SERVER_ERROR_DIALOG_MESSAGE_KEY, mDialogErrorMessage);
        outState.putBoolean(SERVER_ERROR_DIALOG_NEED_TO_SHOW_KEY, mNeedToShowErrorDialog);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mDialogErrorMessage = savedInstanceState.getString(SERVER_ERROR_DIALOG_MESSAGE_KEY);
        mNeedToShowErrorDialog = savedInstanceState.getBoolean(SERVER_ERROR_DIALOG_NEED_TO_SHOW_KEY);
    }
}
