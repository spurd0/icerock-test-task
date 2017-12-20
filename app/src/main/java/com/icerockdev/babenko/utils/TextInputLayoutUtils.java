package com.icerockdev.babenko.utils;

import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Field;

public final class TextInputLayoutUtils {
    private static final String LOG_TAG = TextInputLayoutUtils.class.getSimpleName();

    private static final Field FIELD_INDICATOR_AREA;

    static {
        Field fieldIndicatorArea = null;

        try {
            fieldIndicatorArea = TextInputLayout.class.getDeclaredField("mIndicatorArea");
            fieldIndicatorArea.setAccessible(true);
        } catch (NoSuchFieldException e) {
            Log.w(LOG_TAG, e);
        }

        FIELD_INDICATOR_AREA = fieldIndicatorArea;
    }

    public static void setText(TextInputLayout textInputLayout, CharSequence text) {
        EditText editText = textInputLayout.getEditText();
        if (editText != null) {
            editText.setText(text);
        }
    }

    public static void setText(TextInputLayout textInputLayout, int text) {
        EditText editText = textInputLayout.getEditText();
        if (editText != null) {
            editText.setText(text);
        }
    }

    public static Editable getEditable(TextInputLayout textInputLayout) {
        EditText editText = textInputLayout.getEditText();
        if (editText != null) {
            return editText.getText();
        }
        return null;
    }

    public static String getTextString(TextInputLayout textInputLayout) {
        Editable editable = getEditable(textInputLayout);
        if (editable != null) {
            return editable.toString();
        } else {
            return null;
        }
    }

    public static void setError(TextInputLayout textInputLayout, CharSequence error) {
        EditText editText = textInputLayout.getEditText();
        if (editText != null) {
            if (error != null) {
                textInputLayout.setError(error);
                textInputLayout.setErrorEnabled(true);
            } else {
                textInputLayout.setError(null);
                textInputLayout.setErrorEnabled(false);
                removeIndicatorFix(textInputLayout);
            }
        }
    }

    public static void setError(TextInputLayout textInputLayout, int error) {
        if (error != 0) {
            setError(textInputLayout, textInputLayout.getContext().getString(error));
        } else {
            setError(textInputLayout, null);
        }
    }

    public static void setEnabled(TextInputLayout textInputLayout, boolean enabled) {
        EditText editText = textInputLayout.getEditText();
        if (editText != null) {
            editText.setEnabled(enabled);
        }
    }

    public static boolean isEnabled(TextInputLayout textInputLayout) {
        EditText editText = textInputLayout.getEditText();
        return (editText != null) && editText.isEnabled();
    }

    public static void setTypeface(TextInputLayout textInputLayout, Typeface typeface) {
        EditText editText = textInputLayout.getEditText();
        if (editText != null) {
            editText.setTypeface(typeface);
        }
    }

    /**
     * Проверяет введен ли текст, если не введен - выводит сообщение об ошибке
     *
     * @param textInputLayout input layout
     * @param errorIfEmpty    текст ошибки, если ввод пустой
     * @return true - текст введен, false - текст не введен
     */
    public static boolean checkEmptyWithError(TextInputLayout textInputLayout, CharSequence errorIfEmpty) {
        String text = getTextString(textInputLayout);
        if (text != null && !text.isEmpty()) {
            setError(textInputLayout, null);
            return true;
        } else {
            setError(textInputLayout, errorIfEmpty);
            return false;
        }
    }

    /**
     * Проверяет введен ли текст, если не введен - выводит сообщение об ошибке
     *
     * @param textInputLayout input layout
     * @param errorIfEmpty    текст ошибки, если ввод пустой
     * @return true - текст введен, false - текст не введен
     */
    public static boolean checkEmptyWithError(TextInputLayout textInputLayout, int errorIfEmpty) {
        return checkEmptyWithError(textInputLayout, textInputLayout.getContext().getString(errorIfEmpty));
    }

    public static void setOnClickListener(TextInputLayout textInputLayout, View.OnClickListener listener) {
        EditText editText = textInputLayout.getEditText();
        if (editText != null) {
            editText.setOnClickListener(listener);
        }
    }

    public static void setOnEditorActionListener(TextInputLayout textInputLayout, TextView.OnEditorActionListener onEditorActionListener) {
        EditText editText = textInputLayout.getEditText();
        if (editText != null) {
            editText.setOnEditorActionListener(onEditorActionListener);
        }
    }

    public static void setOnFocusChangeListener(TextInputLayout textInputLayout, TextView.OnFocusChangeListener onFocusChangeListener) {
        EditText editText = textInputLayout.getEditText();
        if (editText != null) {
            editText.setOnFocusChangeListener(onFocusChangeListener);
        }
    }

    public static void addTextChangedListener(TextInputLayout textInputLayout, TextWatcher textWatcher) {
        EditText editText = textInputLayout.getEditText();
        if (editText != null) {
            editText.addTextChangedListener(textWatcher);
        }
    }

    public static void removeTextChangedListener(TextInputLayout textInputLayout, TextWatcher textWatcher) {
        EditText editText = textInputLayout.getEditText();
        if (editText != null) {
            editText.removeTextChangedListener(textWatcher);
        }
    }

    private static void removeIndicatorFix(TextInputLayout textInputLayout) {
        if (FIELD_INDICATOR_AREA == null) return;

        try {
            final Object object = FIELD_INDICATOR_AREA.get(textInputLayout);

            if (!(object instanceof ViewGroup)) return;

            if (((ViewGroup) object).getChildCount() == 0) {
                FIELD_INDICATOR_AREA.set(textInputLayout, null);
            }
        } catch (IllegalAccessException e) {
            Log.w(LOG_TAG, e);
        }
    }
}
