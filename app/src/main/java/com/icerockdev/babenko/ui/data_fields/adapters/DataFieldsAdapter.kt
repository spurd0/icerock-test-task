package com.icerockdev.babenko.ui.data_fields.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.design.widget.TextInputEditText
import android.support.v4.content.ContextCompat
import android.support.v4.util.SparseArrayCompat
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText

import com.icerockdev.babenko.R
import com.icerockdev.babenko.model.entities.DataField
import com.icerockdev.babenko.utils.UtilsHelper

/**
 * Created by Roman Babenko on 01/05/17.
 */

class DataFieldsAdapter(context: Context, dataFields: List<DataField>) :
    BaseListAdapter<DataField>(context, dataFields) {

    // TODO: 5/22/2017 how to get all values?
    val fieldValues = SparseArrayCompat<EditText>()
    private var mDefaultBackground: Drawable? = null

    public override fun getView(position: Int, parent: ViewGroup): View {
        val dataElement = getItem(position)
        val inflater = mContext
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val convertView = inflater.inflate(R.layout.item_data_field, parent, false)
        val mFieldValue = convertView.findViewById<View>(R.id.dataFieldValue) as TextInputEditText
        mDefaultBackground = mFieldValue.background

        val textWatcher = DataFieldsTextWatcher(dataElement)
        mFieldValue.tag = dataElement.id
        mFieldValue.id = dataElement.id!!
        mFieldValue.addTextChangedListener(textWatcher)

        val value = dataElement.value

        mFieldValue.setText(value)
        mFieldValue.hint = UtilsHelper.getInputHint(dataElement.type!!, mContext)
        mFieldValue.inputType = UtilsHelper.getInputType(dataElement.type!!)

        fieldValues.put(mFieldValue.tag as Int, mFieldValue)

        return convertView
    }

    fun updateErrorsViews(errorList: List<Int>) {
        for (j in 0 until fieldValues.size()) {
            fieldValues.get(fieldValues.keyAt(j)).error = null
            fieldValues.get(fieldValues.keyAt(j)).background = mDefaultBackground
        }
        for (i in errorList)
            for (j in 0 until fieldValues.size())
                if (fieldValues.get(fieldValues.keyAt(j)).tag as Int == i) {
                    fieldValues.get(fieldValues.keyAt(j)).error =
                            mContext.getString(R.string.data_field_incorrect_format)
                    fieldValues.get(fieldValues.keyAt(j))
                        .setBackgroundColor(ContextCompat.getColor(mContext, R.color.errorTextColor))
                }
    }

    private inner class DataFieldsTextWatcher(private val mValue: DataField) : TextWatcher {

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            mValue.value = s.toString()
        }

        override fun afterTextChanged(editable: Editable) {}
    }
}
