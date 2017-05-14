package com.icerockdev.babenko.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.data.ApplicationConstants;
import com.icerockdev.babenko.fragments.ProgressDialogFragment;
import com.icerockdev.babenko.fragments.ServerErrorDialogFragment;
import com.icerockdev.babenko.interfaces.HomeView;
import com.icerockdev.babenko.model.DataField;
import com.icerockdev.babenko.presenters.HomePresenter;
import com.icerockdev.babenko.utils.UtilsHelper;

import static com.icerockdev.babenko.fragments.ServerErrorDialogFragment.DIALOG_MESSAGE_KEY;
import static com.icerockdev.babenko.managers.DataFieldsManager.SERVER_ERROR_DIALOG_MESSAGE_KEY;

public class HomeActivity extends AppCompatActivity implements HomeView {
    private static final String SERVER_ERROR_DIALOG_TAG = "com.icerockdev.babenko.activities.SERVER_ERROR_DIALOG_TAG";
    private static final String PROGRESS_DIALOG_TAG = "com.icerockdev.babenko.activities.PROGRESS_DIALOG_TAG";
    private static final String TAG = "HomeActivity";
    private EditText mRequestUrlEditText;
    private HomePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mPresenter = new HomePresenter(this);
        initViews();
    }

    private void initViews() {
        mRequestUrlEditText = (EditText) findViewById(R.id.fieldsRequestUrlEditText);
        mRequestUrlEditText.setText(ApplicationConstants.URL_START);
        mRequestUrlEditText.setSelection(mRequestUrlEditText.getText().length());
    }

    public void requestDataFieldsButtonClicked(View v) {
        mPresenter.requestDataClicked(mRequestUrlEditText.getText().toString());
    }

    public void showErrorDialog(String error) {
        ServerErrorDialogFragment serverErrorDialogFragment = new ServerErrorDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putString(DIALOG_MESSAGE_KEY, error);
        serverErrorDialogFragment.setArguments(arguments);
        serverErrorDialogFragment.show(getSupportFragmentManager(), SERVER_ERROR_DIALOG_TAG);
        UtilsHelper.saveStringToSharedPreferences(this, SERVER_ERROR_DIALOG_MESSAGE_KEY, "");
    }

    public void gotDataFields(DataField[] data) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "Data field count is " + data.length);
        DataFieldsActivity.startActivity(this, data);
    }

    @Override
    public void showProgressDialog() {
        ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();
        progressDialogFragment.show(getSupportFragmentManager(), PROGRESS_DIALOG_TAG);
    }

    @Override
    public void dismissProgressDialog() {
        ProgressDialogFragment progressDialogFragment = (ProgressDialogFragment) getSupportFragmentManager().
                findFragmentByTag(PROGRESS_DIALOG_TAG);
        if (progressDialogFragment != null)
            progressDialogFragment.dismiss();
    }

    @Override
    public void showUrlError(String error) {
        mRequestUrlEditText.setError(error);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.detachView();
        dismissProgressDialog();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.attachView(this);
    }
}
