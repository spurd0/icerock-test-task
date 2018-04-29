package com.icerockdev.babenko.ui.home;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.core.ApplicationConstants;
import com.icerockdev.babenko.databinding.ActivityHomeBinding;
import com.icerockdev.babenko.model.entities.DataField;
import com.icerockdev.babenko.repo.DataFieldsRepository;
import com.icerockdev.babenko.ui.base.activities.BaseProgressActivity;
import com.icerockdev.babenko.ui.base.fragments.ServerErrorDialogFragment;
import com.icerockdev.babenko.ui.data_fields.DataFieldsActivity;
import com.icerockdev.babenko.utils.ErrorCleaningWatcher;
import com.icerockdev.babenko.utils.UtilsHelper;

import javax.inject.Inject;

import timber.log.Timber;

import static com.icerockdev.babenko.ui.base.fragments.ServerErrorDialogFragment.DIALOG_MESSAGE_KEY;

public class HomeActivity extends BaseProgressActivity implements HomeView {
    private static final String SERVER_ERROR_DIALOG_TAG = "HomeActivity.SERVER_ERROR_DIALOG_TAG";
    private static final String TAG = "HomeActivity";
    @InjectPresenter
    HomePresenter mPresenter;

    @Inject
    DataFieldsRepository dataFieldsRepository;

    private ActivityHomeBinding mBinding;
    private TextWatcher mDataFieldsUrlTextWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        mBinding.getRoot().postDelayed(() -> {
            final AnimatorSet animatorSet = UtilsHelper.getMoveScalingAnimator(mBinding.container, mBinding.splashImageView, mBinding.getRoot(),
                    0, 0, 300, 0);
            animatorSet.start();
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    mBinding.setIsAnimationLoaded(true);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }, ApplicationConstants.ANIMATION_DURATION);
        initViews();
    }

    @ProvidePresenter
    HomePresenter provideHomePresenter() {
        IceRockApplication.getAppComponent().inject(this);
        return new HomePresenter(new HomeInteractorImpl(dataFieldsRepository));
    }

    @Override
    protected void setDialogFragmentTag() {
        mDialogTag = "HomeActivity.PROGRESS_DIALOG_TAG";
    }

    private void initViews() {
        mBinding.fieldsRequestUrlEditText.setText(getString(R.string.url_start));
        mBinding.fieldsRequestUrlEditText.setSelection(mBinding.fieldsRequestUrlEditText.getText().length());
        if (BuildConfig.DEBUG) {
            mBinding.fieldsRequestUrlEditText.setText("http://www.mocky.io/v2/58fa10ce110000b81ad2106c");
        }
        initWatchers();
    }

    private void initWatchers() {
        mDataFieldsUrlTextWatcher = new ErrorCleaningWatcher(mBinding.fieldsRequestUrlInput);
        mBinding.fieldsRequestUrlEditText.addTextChangedListener(mDataFieldsUrlTextWatcher);
    }

    @Override
    protected void onDestroy() {
        releaseWatchers();
        super.onDestroy();
    }

    private void releaseWatchers() {
        mBinding.fieldsRequestUrlEditText.removeTextChangedListener(mDataFieldsUrlTextWatcher);
        mDataFieldsUrlTextWatcher = null;
    }

    public void requestDataFieldsButtonClicked(View v) {
        mPresenter.requestDataClicked(mBinding.fieldsRequestUrlEditText.getText().toString());
    }

    public void showErrorDialog() {
        ServerErrorDialogFragment serverErrorDialogFragment = new ServerErrorDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putString(DIALOG_MESSAGE_KEY, getString(R.string.request_data_fields_error_other));
        serverErrorDialogFragment.setArguments(arguments);
        serverErrorDialogFragment.show(getSupportFragmentManager(), SERVER_ERROR_DIALOG_TAG);
    }

    public void gotDataFields(DataField[] data) {
        Timber.tag(TAG).d("Data field count is " + data.length);
        DataFieldsActivity.startActivity(this, data);
    }

    @Override
    public void showUrlError() {
        mBinding.fieldsRequestUrlEditText.setError(getString(R.string.url_error));
    }

}
