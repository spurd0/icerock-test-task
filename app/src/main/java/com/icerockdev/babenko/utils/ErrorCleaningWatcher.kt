package com.icerockdev.babenko.utils

import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher

import java.lang.ref.WeakReference

class ErrorCleaningWatcher(textInputLayout: TextInputLayout) : TextWatcher {
    private val mLayoutWeakReference: WeakReference<TextInputLayout>

    init {
        mLayoutWeakReference = WeakReference(textInputLayout)
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable) {
        val textInputLayout = mLayoutWeakReference.get()
        if (textInputLayout != null) {
            TextInputLayoutUtils.setError(textInputLayout, 0)
        }
    }
}