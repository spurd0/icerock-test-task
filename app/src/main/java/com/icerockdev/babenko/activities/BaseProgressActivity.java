package com.icerockdev.babenko.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.icerockdev.babenko.fragments.ProgressDialogFragment;
import com.icerockdev.babenko.interfaces.ProgressBaseView;

/**
 * Created by Roman Babenko on 14/05/17.
 */

public abstract class BaseProgressActivity extends AppCompatActivity implements ProgressBaseView {
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
        ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();
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
    protected void onPause() {
        dismissProgressDialog();
        super.onPause();
    }
}