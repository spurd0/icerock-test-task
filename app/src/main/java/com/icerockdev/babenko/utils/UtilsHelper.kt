package com.icerockdev.babenko.utils

import android.content.Context
import android.net.ConnectivityManager
import android.text.InputType
import android.util.TypedValue
import com.icerockdev.babenko.R
import com.icerockdev.babenko.core.ApplicationConstants.EMAIL
import com.icerockdev.babenko.core.ApplicationConstants.NUMBER
import com.icerockdev.babenko.core.ApplicationConstants.PHONE
import com.icerockdev.babenko.core.ApplicationConstants.TEXT
import com.icerockdev.babenko.core.ApplicationConstants.URL

/**
 * Created by Roman Babenko on 03/05/17.
 */

object UtilsHelper {
    fun convertDpToPx(context: Context, dpValue: Float): Int {
        val r = context.resources
        return TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dpValue,
                r.displayMetrics
        ).toInt()
    }

    fun getInputType(type: String): Int {
        when (type) {
            TEXT -> return InputType.TYPE_CLASS_TEXT
            EMAIL -> return InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
            PHONE -> return InputType.TYPE_CLASS_PHONE
            NUMBER -> return InputType.TYPE_CLASS_NUMBER
            URL -> return InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS
            else -> throw IllegalArgumentException("Unknown type")
        }
    }

    fun getInputHint(type: String, context: Context): String {
        when (type) {
            TEXT -> return context.getString(R.string.data_field_text_hint)
            EMAIL -> return context.getString(R.string.data_field_email_hint)
            PHONE -> return context.getString(R.string.data_field_phone_hint)
            NUMBER -> return context.getString(R.string.data_field_number_hint)
            URL -> return context.getString(R.string.data_field_url_hint)
            else -> throw IllegalArgumentException("Unknown type")
        }
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}
