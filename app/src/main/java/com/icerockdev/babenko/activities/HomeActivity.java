package com.icerockdev.babenko.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.data.ApplicationConstants;
import com.icerockdev.babenko.fragments.ServerErrorDialogFragment;
import com.icerockdev.babenko.interfaces.HomeView;
import com.icerockdev.babenko.managers.impl.HomeManagerImpl;
import com.icerockdev.babenko.managers.impl.SharedPreferencesManagerImpl;
import com.icerockdev.babenko.managers.interfaces.SharedPreferencesManager;
import com.icerockdev.babenko.model.DataField;
import com.icerockdev.babenko.presenters.HomePresenter;

import javax.inject.Inject;

import static com.icerockdev.babenko.fragments.ServerErrorDialogFragment.DIALOG_MESSAGE_KEY;
import static com.icerockdev.babenko.presenters.HomePresenter.CODE_ERROR_EMPTY_LIST;
import static com.icerockdev.babenko.presenters.HomePresenter.CODE_ERROR_LIST_NULL_RESPONSE;
import static com.icerockdev.babenko.presenters.HomePresenter.CODE_ERROR_OTHER;

public class HomeActivity extends BaseProgressActivity implements HomeView {
    private static final String SERVER_ERROR_DIALOG_TAG = "com.icerockdev.babenko.activities.SERVER_ERROR_DIALOG_TAG";
    private static final String TAG = "HomeActivity";
    private EditText mRequestUrlEditText;
    private HomePresenter mPresenter;
    private SharedPreferencesManager mSharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mSharedPreferencesManager = new SharedPreferencesManagerImpl();
        mPresenter = new HomePresenter(new HomeManagerImpl(), mSharedPreferencesManager);
        initViews();
    }

    @Override
    protected void setDialogFragmentTag() {
        mDialogTag = "com.icerockdev.babenko.activities.HomeActivity.PROGRESS_DIALOG_TAG";
    }

    private void initViews() {
        mRequestUrlEditText = (EditText) findViewById(R.id.fieldsRequestUrlEditText);
        mRequestUrlEditText.setText(ApplicationConstants.URL_START);
        mRequestUrlEditText.setSelection(mRequestUrlEditText.getText().length());
        if (BuildConfig.DEBUG)
            mRequestUrlEditText.setText("http://www.mocky.io/v2/58fa10ce110000b81ad2106c");
    }

    public void requestDataFieldsButtonClicked(View v) {
        mPresenter.requestDataClicked(mRequestUrlEditText.getText().toString());
    }

    public void showErrorDialog(int codeError) {
        ServerErrorDialogFragment serverErrorDialogFragment = new ServerErrorDialogFragment();
        Bundle arguments = new Bundle();
        String error;
        switch (codeError) {
            case CODE_ERROR_EMPTY_LIST:
            error = getString(R.string.request_data_fields_error_list_empty);
                break;
            case CODE_ERROR_LIST_NULL_RESPONSE:
                error = getString(R.string.request_data_fields_error_null);
                break;
            case CODE_ERROR_OTHER:
                error = getString(R.string.request_data_fields_error_other);
                break;
            default:
                error = getString(R.string.request_data_fields_error_other);
                break;
        }
        arguments.putString(DIALOG_MESSAGE_KEY, error);
        serverErrorDialogFragment.setArguments(arguments);
        serverErrorDialogFragment.show(getSupportFragmentManager(), SERVER_ERROR_DIALOG_TAG);
    }

    public void gotDataFields(DataField[] data) {
        if (BuildConfig.DEBUG)
            Log.d(TAG, "Data field count is " + data.length);
        DataFieldsActivity.startActivity(this, data);
    }

    @Override
    public void showUrlError() {
        mRequestUrlEditText.setError(getString(R.string.url_error));
    }

    @Override
    protected void onPause() {
        mPresenter.detachView();
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.attachView(this);
    }
}
