package com.icerockdev.babenko.activities;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;

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
    private EditText mRequestUrlEditText;

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
        serverErrorDialogFragment.show(getSupportFragmentManager(), SERVER_ERROR_DIALOG_TAG);
    }

    private void gotDataFields(DataField[] data) {

    }

}
