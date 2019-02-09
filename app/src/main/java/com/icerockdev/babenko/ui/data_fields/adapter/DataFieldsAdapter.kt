package com.icerockdev.babenko.ui.data_fields.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.util.SparseIntArray
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import com.icerockdev.babenko.R
import com.icerockdev.babenko.applicaiton.utils.getInputHint
import com.icerockdev.babenko.applicaiton.utils.getInputType
import com.icerockdev.babenko.model.entities.DataField
import com.icerockdev.babenko.ui.data_fields.adapter.holder.DataFieldFooterViewHolder
import com.icerockdev.babenko.ui.data_fields.adapter.holder.DataFieldHeaderViewHolder
import com.icerockdev.babenko.ui.data_fields.adapter.holder.DataFieldItemViewHolder

/**
 * Created by Roman Babenko on 01/05/17.
 */

class DataFieldsAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_HEADER = 1
        private const val TYPE_DATA_FIELD = 2
        private const val TYPE_FOOTER = 3
    }

    private val containerLayoutRes = object : SparseIntArray() {
        init {
            put(TYPE_HEADER, R.layout.item_data_field_header)
            put(TYPE_DATA_FIELD, R.layout.item_data_field)
            put(TYPE_FOOTER, R.layout.item_data_field_footer)
        }
    }
    private val inflater = LayoutInflater.from(context)
    private val items = ArrayList<AdapterItem>()
    private val fieldValues = SparseArrayCompat<EditText>()
    private var defaultBackground: Drawable? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> DataFieldHeaderViewHolder(
                inflater.inflate(
                    containerLayoutRes.get(viewType),
                    parent,
                    false
                )
            )
            TYPE_DATA_FIELD -> {
                val view = inflater.inflate(
                    containerLayoutRes.get(viewType),
                    parent,
                    false
                )
                val vh = DataFieldItemViewHolder(view)
                if (defaultBackground == null) defaultBackground = vh.mFieldValue.background
                return vh
            }
            else -> DataFieldFooterViewHolder(
                inflater.inflate(
                    containerLayoutRes.get(viewType),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].itemViewType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dataElement = items[position]
        val viewType = dataElement.itemViewType
        when (viewType) {
            TYPE_DATA_FIELD -> {
                val item = dataElement as DataFieldAdapterItem
                onBindDataField(holder as DataFieldItemViewHolder, item.dataField)
            }
        }
    }

    private fun onBindDataField(
        holder: DataFieldItemViewHolder,
        dataElement: DataField
    ) {
        val textWatcher = DataFieldsTextWatcher(dataElement)
        holder.mFieldValue.tag = dataElement.id
        holder.mFieldValue.id = dataElement.id
        holder.mFieldValue.addTextChangedListener(textWatcher) // todo fix it

        val value = dataElement.value

        holder.mFieldValue.setText(value)
        holder.mFieldValue.hint = getInputHint(dataElement.type, context)
        holder.mFieldValue.inputType = getInputType(dataElement.type)

        fieldValues.put(holder.mFieldValue.tag as Int, holder.mFieldValue)
    }

    fun replaceItems(dataFields: List<DataField>) {
        clearDataFields()
        for (dataField in dataFields) {
            items.add(DataFieldAdapterItem(dataField))
        }
    }

    private fun clearDataFields() {
        val iterator = items.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item.itemViewType == TYPE_DATA_FIELD) iterator.remove()
        }
    }

    fun updateErrorsViews(errorList: List<Int>) {
        for (j in 0 until fieldValues.size()) {
            fieldValues.get(fieldValues.keyAt(j)).error = null
            fieldValues.get(fieldValues.keyAt(j)).background = defaultBackground
        }
        for (i in errorList)
            for (j in 0 until fieldValues.size())
                if (fieldValues.get(fieldValues.keyAt(j)).tag as Int == i) {
                    fieldValues.get(fieldValues.keyAt(j)).error =
                            context.getString(R.string.data_field_incorrect_format)
                    fieldValues.get(fieldValues.keyAt(j))
                        .setBackgroundColor(ContextCompat.getColor(context, R.color.errorTextColor))
                }
    }

    fun getFieldValues(): SparseArrayCompat<EditText> {
        return fieldValues
    }

    interface AdapterItem {
        val itemViewType: Int
    }

    data class DataFieldAdapterItem(val dataField: DataField) :
        AdapterItem {
        override val itemViewType = TYPE_DATA_FIELD
    }
}