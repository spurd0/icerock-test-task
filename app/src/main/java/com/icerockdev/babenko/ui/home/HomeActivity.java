package com.icerockdev.babenko.ui.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.core.ApplicationConstants;
import com.icerockdev.babenko.databinding.ActivityHomeBinding;
import com.icerockdev.babenko.models.impl.HomeModelImpl;
import com.icerockdev.babenko.managers.impl.SharedPreferencesManagerImpl;
import com.icerockdev.babenko.model.DataField;
import com.icerockdev.babenko.ui.BaseProgressActivity;
import com.icerockdev.babenko.ui.data_fields.DataFieldsActivity;
import com.icerockdev.babenko.ui.fragments.ServerErrorDialogFragment;

import static com.icerockdev.babenko.ui.fragments.ServerErrorDialogFragment.DIALOG_MESSAGE_KEY;
import static com.icerockdev.babenko.ui.home.HomePresenter.CODE_ERROR_EMPTY_LIST;
import static com.icerockdev.babenko.ui.home.HomePresenter.CODE_ERROR_LIST_NULL_RESPONSE;
import static com.icerockdev.babenko.ui.home.HomePresenter.CODE_ERROR_OTHER;

public class HomeActivity extends BaseProgressActivity implements HomeView {
    private static final String SERVER_ERROR_DIALOG_TAG = "com.icerockdev.babenko.activities.SERVER_ERROR_DIALOG_TAG";
    private static final String TAG = "HomeActivity";
    @InjectPresenter
    HomePresenter mPresenter;
    private ActivityHomeBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        initViews();
    }

    @ProvidePresenter
    HomePresenter provideHomePresenter() {
        return new HomePresenter(new HomeModelImpl(), new SharedPreferencesManagerImpl());
    }

    @Override
    protected void setDialogFragmentTag() {
        mDialogTag = "com.icerockdev.babenko.ui.home.HomeActivity.PROGRESS_DIALOG_TAG";
    }

    private void initViews() {
        mBinding.fieldsRequestUrlEditText.setText(ApplicationConstants.URL_START);
        mBinding.fieldsRequestUrlEditText.setSelection(mBinding.fieldsRequestUrlEditText.getText().length());
        if (BuildConfig.DEBUG)
            mBinding.fieldsRequestUrlEditText.setText("http://www.mocky.io/v2/58fa10ce110000b81ad2106c");
    }

    public void requestDataFieldsButtonClicked(View v) {
        mPresenter.requestDataClicked(mBinding.fieldsRequestUrlEditText.getText().toString());
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
        mBinding.fieldsRequestUrlEditText.setError(getString(R.string.url_error));
    }

}
