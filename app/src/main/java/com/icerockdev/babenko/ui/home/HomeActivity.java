package com.icerockdev.babenko.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.model.entities.DataField;
import com.icerockdev.babenko.repo.DataFieldsRepository;
import com.icerockdev.babenko.ui.base.activities.BaseProgressActivity;
import com.icerockdev.babenko.ui.base.fragments.ServerErrorDialogFragment;
import com.icerockdev.babenko.utils.ErrorCleaningWatcher;
import com.icerockdev.babenko.utils.UtilsHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.icerockdev.babenko.ui.base.fragments.ServerErrorDialogFragment.DIALOG_MESSAGE_KEY;

public class HomeActivity extends BaseProgressActivity implements HomeView {
    public static final String FIELDS_LINK = "http://www.mocky.io/v2/58fa10ce110000b81ad2106c";
    private static final String SERVER_ERROR_DIALOG_TAG = "HomeActivity.SERVER_ERROR_DIALOG_TAG";
    private static final String TAG = "HomeActivity";
    @InjectPresenter
    HomePresenter mPresenter;

    @Inject
    DataFieldsRepository dataFieldsRepository;

    @BindView(R.id.fields_request_url_edit_text)
    EditText fieldsRequestUrlEditText;
    @BindView(R.id.fields_request_url_input)

    TextInputLayout fieldsRequestUrlInput;
    private TextWatcher mDataFieldsUrlTextWatcher;

    public static Intent getLaunchingIntent(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initViews();
    }

    @ProvidePresenter
    HomePresenter provideHomePresenter() {
        IceRockApplication.getAppComponent().inject(this);
        return new HomePresenter(new HomeInteractorImpl(dataFieldsRepository));
    }

    private void initViews() {
        fieldsRequestUrlEditText.setText(getString(R.string.url_start));
        fieldsRequestUrlEditText.setSelection(fieldsRequestUrlEditText.getText().length());
        if (BuildConfig.DEBUG) {
            fieldsRequestUrlEditText.setText(FIELDS_LINK);
        }
        initWatchers();
    }

    private void initWatchers() {
        mDataFieldsUrlTextWatcher = new ErrorCleaningWatcher(fieldsRequestUrlInput);
        fieldsRequestUrlEditText.addTextChangedListener(mDataFieldsUrlTextWatcher);
    }

    @Override
    protected void onDestroy() {
        releaseWatchers();
        super.onDestroy();
    }

    private void releaseWatchers() {
        fieldsRequestUrlEditText.removeTextChangedListener(mDataFieldsUrlTextWatcher);
        mDataFieldsUrlTextWatcher = null;
    }

    public void requestDataFieldsButtonClicked(View v) {
        if (UtilsHelper.isNetworkAvailable(this)) {
            mPresenter.requestDataClicked(fieldsRequestUrlEditText.getText().toString());
        } else {
            showErrorDialog(getString(R.string.error_no_network));
        }
    }

    private void showErrorDialog(String error) {
        ServerErrorDialogFragment serverErrorDialogFragment = new ServerErrorDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putString(DIALOG_MESSAGE_KEY, error);
        serverErrorDialogFragment.setArguments(arguments);
        serverErrorDialogFragment.show(getSupportFragmentManager(), SERVER_ERROR_DIALOG_TAG);
    }

    public void gotDataFields(DataField[] data) {
        Timber.tag(TAG).d("Data field count is:%s", data.length);
        navigator.navigateToDataFieldsActivity(this, data);
    }

    @Override
    public void showTimeoutError() {
        showErrorDialog(getString(R.string.error_timeout));
    }

    @Override
    public void showErrorDialog() {
        showErrorDialog(getString(R.string.error_other));
    }

    @Override
    public void showUrlError() {
        fieldsRequestUrlEditText.setError(getString(R.string.url_error));
    }
}
