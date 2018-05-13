package com.icerockdev.babenko.ui.data_fields

import android.support.v4.util.SparseArrayCompat
import android.util.Patterns
import android.widget.EditText
import com.icerockdev.babenko.core.ApplicationConstants.EMAIL
import com.icerockdev.babenko.core.ApplicationConstants.NUMBER
import com.icerockdev.babenko.core.ApplicationConstants.PHONE
import com.icerockdev.babenko.core.ApplicationConstants.TEXT
import com.icerockdev.babenko.core.ApplicationConstants.URL
import com.icerockdev.babenko.model.entities.DataField
import io.reactivex.Single
import java.util.*
import java.util.regex.Pattern

/**
 * Created by Roman Babenko on 30/04/17.
 */

class DataFieldsInteractorImpl(private val mDataFields: Array<DataField>) : DataFieldsInteractor {
    override fun checkFields(fieldValues: SparseArrayCompat<EditText>): Single<List<Int>> {
        return Single.fromCallable {
            val errorList = ArrayList<Int>()
            for (i in 0 until fieldValues.size()) {
                val key = fieldValues.keyAt(i)
                val fieldValue = fieldValues.get(key)
                for (dataField in mDataFields)
                    if (dataField.id == key) {
                        if (!isDataFieldCorrect(fieldValue.text.toString(), dataField.type))
                            errorList.add(key)
                    }
            }
            errorList
        }
    }

    private fun isDataFieldCorrect(data: String, type: String?): Boolean {
        if (data.isEmpty())
            return false
        when (type) {
            TEXT -> {
                val length = data.length
                return length in 11..29
            }
            EMAIL -> return android.util.Patterns.EMAIL_ADDRESS.matcher(data).matches()
            PHONE -> {
                val phonePattern = Pattern.compile(phoneRegex)
                return phonePattern.matcher(data).matches()
            }
            NUMBER -> {
                val numberPattern = Pattern.compile(numberRegex)
                return numberPattern.matcher(data).matches()
            }
            URL -> {
                return Patterns.WEB_URL.matcher(data).matches()
            }
            else -> {
                throw IllegalArgumentException("Unknown type")
            }
        }
    }

    override fun requestDataFields(): Single<List<DataField>> {
        return Single.fromCallable { Arrays.asList(*mDataFields) }
    }

    companion object {
        const val phoneRegex = "^[+7]{2}\\d{10}$"
        const val numberRegex = "^\\d{1,5}$"
    }
}
