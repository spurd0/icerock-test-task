package com.icerockdev.babenko.utils;

import android.content.Context;
import android.content.res.Resources;
import android.text.InputType;
import android.util.TypedValue;

import com.icerockdev.babenko.R;

import static com.icerockdev.babenko.core.ApplicationConstants.EMAIL;
import static com.icerockdev.babenko.core.ApplicationConstants.NUMBER;
import static com.icerockdev.babenko.core.ApplicationConstants.PHONE;
import static com.icerockdev.babenko.core.ApplicationConstants.TEXT;
import static com.icerockdev.babenko.core.ApplicationConstants.URL;

/**
 * Created by Roman Babenko on 03/05/17.
 */

public class UtilsHelper {
    public static int convertDpToPx(Context context, float dpValue) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dpValue,
                r.getDisplayMetrics()
        );
    }

    public static int getInputType(String type) {
        switch (type) {
            case TEXT:
                return InputType.TYPE_CLASS_TEXT;
            case EMAIL:
                return InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
            case PHONE:
                return InputType.TYPE_CLASS_PHONE;
            case NUMBER:
                return InputType.TYPE_CLASS_NUMBER;
            case URL:
                return InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS;
            default:
                throw new IllegalArgumentException("Unknown type");
        }
    }

    public static String getInputHint(String type, Context context) {
        switch (type) {
            case TEXT:
                return context.getString(R.string.data_field_text_hint);
            case EMAIL:
                return context.getString(R.string.data_field_email_hint);
            case PHONE:
                return context.getString(R.string.data_field_phone_hint);
            case NUMBER:
                return context.getString(R.string.data_field_number_hint);
            case URL:
                return context.getString(R.string.data_field_url_hint);
            default:
                throw new IllegalArgumentException("Unknown type");
        }
    }
}
