package com.icerockdev.babenko.ui.base.activities;

import com.icerockdev.babenko.ui.base.fragments.ProgressDialogFragment;
import com.icerockdev.babenko.ui.base.views.ProgressBaseView;

/**
 * Created by Roman Babenko on 14/05/17.
 */

public abstract class BaseProgressActivity extends BaseActivity implements ProgressBaseView {
    protected String mDialogTag = "BaseProgressActivity.PROGRESS_DIALOG_TAG";

    @Override
    public void showProgressDialog() {
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
        ProgressDialogFragment progressDialogFragment = (ProgressDialogFragment) getSupportFragmentManager().
                findFragmentByTag(mDialogTag);
        if (progressDialogFragment != null) {
            progressDialogFragment.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }
}
