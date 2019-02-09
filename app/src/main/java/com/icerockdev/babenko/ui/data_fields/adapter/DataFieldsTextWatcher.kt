package com.icerockdev.babenko.ui.data_fields.adapter

import android.text.Editable
import android.text.TextWatcher
import com.icerockdev.babenko.model.entities.DataField


class DataFieldsTextWatcher(private val mValue: DataField) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        mValue.value = s.toString()
    }

    override fun afterTextChanged(editable: Editable) {}
}