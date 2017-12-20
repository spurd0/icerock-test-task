package com.icerockdev.babenko.ui.home;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.core.ApplicationConstants;
import com.icerockdev.babenko.core.NetworkApi;
import com.icerockdev.babenko.model.entities.DataField;
import com.icerockdev.babenko.ui.BaseProgressActivity;
import com.icerockdev.babenko.ui.data_fields.DataFieldsActivity;
import com.icerockdev.babenko.ui.fragments.ServerErrorDialogFragment;
import com.icerockdev.babenko.utils.ErrorCleaningWatcher;
import com.icerockdev.babenko.utils.UtilsHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.icerockdev.babenko.ui.fragments.ServerErrorDialogFragment.DIALOG_MESSAGE_KEY;

public class HomeActivity extends BaseProgressActivity implements HomeView {
    private static final String SERVER_ERROR_DIALOG_TAG = HomeActivity.class.getName() + ".SERVER_ERROR_DIALOG_TAG";
    private static final String TAG = HomeActivity.class.getName();

    @InjectPresenter
    HomePresenter mPresenter;

    @Inject
    NetworkApi mNetworkApi;

    @BindView(R.id.fields_request_url_edit_text)
    TextInputEditText mFieldsRequestUrlEditText;

    @BindView(R.id.container)
    FrameLayout rootView;

    @BindView(R.id.splash_image_view)
    ImageView splashImageView;

    @BindView(R.id.fields_request_url_input)
    TextInputLayout mFieldsRequestUrlInput;

    @BindView(R.id.reqeust_fields_container)
    ConstraintLayout mRequestFieldsContainer;

    private TextWatcher mDataFieldsUrlTextWatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        rootView.getRootView().postDelayed(() -> {
            final AnimatorSet animatorSet = UtilsHelper.getMoveScalingAnimator(rootView, splashImageView, rootView.getRootView(),
                    0, 0, 300, 0);
            animatorSet.start();
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    setAnimationLoaded(true);
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

    private void setAnimationLoaded(boolean loaded) {
        splashImageView.setVisibility(loaded ? View.INVISIBLE : View.VISIBLE);
        mRequestFieldsContainer.setVisibility(loaded ? View.VISIBLE : View.INVISIBLE);
    }

    @ProvidePresenter
    HomePresenter provideHomePresenter() {
        IceRockApplication.getAppComponent().inject(this);
        return new HomePresenter(new HomeInteractorImpl(mNetworkApi));
    }

    @Override
    protected void setDialogFragmentTag() {
        mDialogTag = HomeActivity.class.getName() + ".PROGRESS_DIALOG_TAG";
    }

    private void initViews() {
        mFieldsRequestUrlEditText.setText(getString(R.string.url_start));
        mFieldsRequestUrlEditText.setSelection(mFieldsRequestUrlEditText.getText().length());
        if (BuildConfig.DEBUG) {
            mFieldsRequestUrlEditText.setText("http://www.mocky.io/v2/58fa10ce110000b81ad2106c");
        }
        initWatchers();
    }

    private void initWatchers() {
        mDataFieldsUrlTextWatcher = new ErrorCleaningWatcher(mFieldsRequestUrlInput);
        mFieldsRequestUrlEditText.addTextChangedListener(mDataFieldsUrlTextWatcher);
    }

    @Override
    protected void onDestroy() {
        releaseWatchers();
        super.onDestroy();
    }

    private void releaseWatchers() {
        mFieldsRequestUrlEditText.removeTextChangedListener(mDataFieldsUrlTextWatcher);
        mDataFieldsUrlTextWatcher = null;
    }

    public void requestDataFieldsButtonClicked(View v) {
        mPresenter.requestDataClicked(mFieldsRequestUrlEditText.getText().toString());
    }

    public void showErrorDialog() {
        ServerErrorDialogFragment serverErrorDialogFragment = new ServerErrorDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putString(DIALOG_MESSAGE_KEY, getString(R.string.request_data_fields_error_other));
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
        mFieldsRequestUrlEditText.setError(getString(R.string.url_error));
    }

}
