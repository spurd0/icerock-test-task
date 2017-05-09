package com.icerockdev.babenko.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.TypedValue;

import java.util.UnknownFormatConversionException;

import static com.icerockdev.babenko.data.ApplicationConstants.EMAIL;
import static com.icerockdev.babenko.data.ApplicationConstants.NUMBER;
import static com.icerockdev.babenko.data.ApplicationConstants.PHONE;
import static com.icerockdev.babenko.data.ApplicationConstants.TEXT;
import static com.icerockdev.babenko.data.ApplicationConstants.URL;

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

    public static void saveStringToSharedPreferences(Context context, String key, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(key, value).apply();
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
                throw new UnknownFormatConversionException("Unknown type");
        }
    }
}
