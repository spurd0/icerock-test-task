package com.icerockdev.babenko.utils;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;

import java.lang.ref.WeakReference;

public class ErrorCleaningWatcher implements TextWatcher {
    private WeakReference<TextInputLayout> mLayoutWeakReference;

    public ErrorCleaningWatcher(TextInputLayout textInputLayout) {
        mLayoutWeakReference = new WeakReference<>(textInputLayout);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        TextInputLayout textInputLayout = mLayoutWeakReference.get();
        if (textInputLayout != null) {
            TextInputLayoutUtils.setError(textInputLayout, 0);
        }
    }
}