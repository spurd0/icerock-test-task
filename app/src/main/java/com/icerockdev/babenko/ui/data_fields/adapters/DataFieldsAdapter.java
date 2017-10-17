package com.icerockdev.babenko.ui.data_fields.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.SparseArrayCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.icerockdev.babenko.R;
import com.icerockdev.babenko.model.entities.DataField;
import com.icerockdev.babenko.utils.UtilsHelper;

import java.util.List;

/**
 * Created by Roman Babenko on 01/05/17.
 */

public class DataFieldsAdapter extends BaseListAdapter<DataField> {

    private SparseArrayCompat<EditText> mFieldValues = new SparseArrayCompat<EditText>();
    private Drawable mDefaultBackground;

    public DataFieldsAdapter(@NonNull Context context, @NonNull List<DataField> dataFields) {
        super(context, dataFields);
    }

    @NonNull
    @Override
    public View getView(int position, ViewGroup parent) {
        DataField dataElement = getItem(position);
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.data_field_element, parent, false);
        TextInputEditText mFieldValue = (TextInputEditText) convertView.findViewById(R.id.dataFieldValue);
        mDefaultBackground = mFieldValue.getBackground();

        DataFieldsTextWatcher textWatcher = new DataFieldsTextWatcher(dataElement);
        mFieldValue.setTag(dataElement.getId());
        mFieldValue.setId(dataElement.getId());
        mFieldValue.addTextChangedListener(textWatcher);

        String value = dataElement.getValue();

        mFieldValue.setText(value);
        mFieldValue.setHint(UtilsHelper.getInputHint(dataElement.getType(), mContext));
        mFieldValue.setInputType(UtilsHelper.getInputType(dataElement.getType()));

        mFieldValues.put((Integer) mFieldValue.getTag(), mFieldValue);

        return convertView;
    }

    public SparseArrayCompat<EditText> getFieldValues() {
        return mFieldValues; // TODO: 5/22/2017 how to get all values?
    }

    public void updateErrorsViews(List<Integer> errorList) {
        for (int j = 0; j < mFieldValues.size(); j++) {
            mFieldValues.get(mFieldValues.keyAt(j)).setError(null);
            mFieldValues.get(mFieldValues.keyAt(j)).setBackground(mDefaultBackground);
        }
        for (int i : errorList)
            for (int j = 0; j < mFieldValues.size(); j++)
                if (((int) mFieldValues.get(mFieldValues.keyAt(j)).getTag()) == i) {
                    mFieldValues.get(mFieldValues.keyAt(j))
                            .setError(mContext.getString(R.string.data_field_incorrect_format));
                    mFieldValues.get(mFieldValues.keyAt(j))
                            .setBackgroundColor(ContextCompat.getColor(mContext, R.color.errorTextColor));
                }
    }

    private class DataFieldsTextWatcher implements TextWatcher {
        private DataField mValue;

        private DataFieldsTextWatcher(DataField value) {
            mValue = value;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mValue.setValue(s.toString());
        }

        public void afterTextChanged(Editable editable) {
        }
    }
}
