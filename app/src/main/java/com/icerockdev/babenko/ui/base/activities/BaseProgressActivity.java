package com.icerockdev.babenko.ui.base.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.icerockdev.babenko.ui.base.views.ProgressBaseView;
import com.icerockdev.babenko.ui.base.fragments.ProgressDialogFragment;

/**
 * Created by Roman Babenko on 14/05/17.
 */

public abstract class BaseProgressActivity extends BaseActivity implements ProgressBaseView {
    protected String mDialogTag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDialogFragmentTag();
    }

    protected abstract void setDialogFragmentTag();

    @Override
    public void showProgressDialog() {
        if (mDialogTag == null)
            throw new NullPointerException("mDialogTag isn`t defined");
        ProgressDialogFragment progressDialogFragment = (ProgressDialogFragment) getSupportFragmentManager().
                findFragmentByTag(mDialogTag);
        if (progressDialogFragment != null)
            return;
        progressDialogFragment = new ProgressDialogFragment();
        getSupportFragmentManager().beginTransaction().add(progressDialogFragment, mDialogTag).commit();
        getSupportFragmentManager().executePendingTransactions();

    }

    @Override
    public void dismissProgressDialog() {
        ProgressDialogFragment progressDialogFragment = (ProgressDialogFragment) getSupportFragmentManager().
                findFragmentByTag(mDialogTag);
        if (progressDialogFragment != null)
            progressDialogFragment.dismiss();
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }
}
