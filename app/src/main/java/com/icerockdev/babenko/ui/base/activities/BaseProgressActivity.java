package com.icerockdev.babenko.ui.base.activities;

import android.os.Bundle;

import com.icerockdev.babenko.ui.base.fragments.ProgressDialogFragment;
import com.icerockdev.babenko.ui.base.views.ProgressBaseView;

/**
 * Created by Roman Babenko on 14/05/17.
 */

public abstract class BaseProgressActivity extends BaseActivity implements ProgressBaseView {
    protected String mDialogTag = "BaseProgressActivity.PROGRESS_DIALOG_TAG";
    private boolean allowToShowDialog;

    @Override
    public void showProgressDialog() {
        if (!allowToShowDialog) {
            return;
        }
        ProgressDialogFragment progressDialogFragment = (ProgressDialogFragment) getSupportFragmentManager().
                findFragmentByTag(mDialogTag);
        if (progressDialogFragment != null) {
            return;
        }
        progressDialogFragment = new ProgressDialogFragment();
        getSupportFragmentManager().beginTransaction().add(progressDialogFragment, mDialogTag).commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    @Override
    public void dismissProgressDialog() {
        if (!allowToShowDialog) {
            return;
        }
        ProgressDialogFragment progressDialogFragment = (ProgressDialogFragment) getSupportFragmentManager().
                findFragmentByTag(mDialogTag);
        if (progressDialogFragment != null) {
            progressDialogFragment.dismiss();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        allowToShowDialog = false;
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        allowToShowDialog = true;
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }
}
