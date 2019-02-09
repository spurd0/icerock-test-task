package com.icerockdev.babenko.applicaiton.utils

import android.graphics.Typeface
import android.support.design.widget.TextInputLayout
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import timber.log.Timber
import java.lang.reflect.Field

object TextInputLayoutUtils {
    private val TAG = "TextInputLayoutUtils"

    private val FIELD_INDICATOR_AREA: Field?

    init {
        var fieldIndicatorArea: Field? = null

        try {
            fieldIndicatorArea = TextInputLayout::class.java.getDeclaredField("mIndicatorArea")
            fieldIndicatorArea!!.isAccessible = true
        } catch (e: NoSuchFieldException) {
            Timber.tag(TAG).w(e)
        }

        FIELD_INDICATOR_AREA = fieldIndicatorArea
    }

    fun setText(textInputLayout: TextInputLayout, text: CharSequence) {
        val editText = textInputLayout.editText
        editText?.setText(text)
    }

    fun setText(textInputLayout: TextInputLayout, text: Int) {
        val editText = textInputLayout.editText
        editText?.setText(text)
    }

    fun getEditable(textInputLayout: TextInputLayout): Editable? {
        val editText = textInputLayout.editText
        return editText?.text
    }

    fun getTextString(textInputLayout: TextInputLayout): String? {
        val editable = getEditable(textInputLayout)
        return editable?.toString()
    }

    fun setError(textInputLayout: TextInputLayout, error: CharSequence?) {
        val editText = textInputLayout.editText
        if (editText != null) {
            if (error != null) {
                textInputLayout.error = error
                textInputLayout.isErrorEnabled = true
            } else {
                textInputLayout.error = null
                textInputLayout.isErrorEnabled = false
                removeIndicatorFix(textInputLayout)
            }
        }
    }

    fun setError(textInputLayout: TextInputLayout, error: Int) {
        if (error != 0) {
            setError(textInputLayout, textInputLayout.context.getString(error))
        } else {
            setError(textInputLayout, null)
        }
    }

    fun setEnabled(textInputLayout: TextInputLayout, enabled: Boolean) {
        val editText = textInputLayout.editText
        if (editText != null) {
            editText.isEnabled = enabled
        }
    }

    fun isEnabled(textInputLayout: TextInputLayout): Boolean {
        val editText = textInputLayout.editText
        return editText != null && editText.isEnabled
    }

    fun setTypeface(textInputLayout: TextInputLayout, typeface: Typeface) {
        val editText = textInputLayout.editText
        if (editText != null) {
            editText.typeface = typeface
        }
    }

    /**
     * Проверяет введен ли текст, если не введен - ставит сообщение об ошибке
     *
     * @param textInputLayout input layout
     * @param errorIfEmpty    текст ошибки, если ввод пустой
     * @return true - текст введен, false - текст не введен
     */
    fun checkEmptyWithError(textInputLayout: TextInputLayout, errorIfEmpty: CharSequence): Boolean {
        val text = getTextString(textInputLayout)
        if (text != null && !text.isEmpty()) {
            setError(textInputLayout, null)
            return true
        } else {
            setError(textInputLayout, errorIfEmpty)
            return false
        }
    }

    /**
     * Проверяет введен ли текст, если не введен - ставит сообщение об ошибке
     *
     * @param textInputLayout input layout
     * @param errorIfEmpty    текст ошибки, если ввод пустой
     * @return true - текст введен, false - текст не введен
     */
    fun checkEmptyWithError(textInputLayout: TextInputLayout, errorIfEmpty: Int): Boolean {
        return checkEmptyWithError(textInputLayout, textInputLayout.context.getString(errorIfEmpty))
    }

    fun setOnClickListener(textInputLayout: TextInputLayout, listener: View.OnClickListener) {
        val editText = textInputLayout.editText
        editText?.setOnClickListener(listener)
    }

    fun setOnEditorActionListener(
        textInputLayout: TextInputLayout,
        onEditorActionListener: TextView.OnEditorActionListener
    ) {
        val editText = textInputLayout.editText
        editText?.setOnEditorActionListener(onEditorActionListener)
    }

    fun addTextChangedListener(textInputLayout: TextInputLayout, textWatcher: TextWatcher) {
        val editText = textInputLayout.editText
        editText?.addTextChangedListener(textWatcher)
    }

    fun removeTextChangedListener(textInputLayout: TextInputLayout, textWatcher: TextWatcher) {
        val editText = textInputLayout.editText
        editText?.removeTextChangedListener(textWatcher)
    }

    private fun removeIndicatorFix(textInputLayout: TextInputLayout) {
        if (FIELD_INDICATOR_AREA == null) return

        try {
            val viewArea = FIELD_INDICATOR_AREA.get(textInputLayout) as? ViewGroup ?: return

            if (viewArea.childCount == 0) {
                FIELD_INDICATOR_AREA.set(textInputLayout, null)
            }
        } catch (e: IllegalAccessException) {
            Timber.tag(TAG).w(e)
        }

    }
}