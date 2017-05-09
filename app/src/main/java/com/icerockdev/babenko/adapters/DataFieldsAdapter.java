package com.icerockdev.babenko.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.icerockdev.babenko.R;
import com.icerockdev.babenko.model.DataField;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Roman Babenko on 01/05/17.
 */

public class DataFieldsAdapter extends BaseListAdapter<DataField> {

    private HashMap<String, String> mFieldValues = new HashMap<String, String>();

    public DataFieldsAdapter(@NonNull Context context, @NonNull List<DataField> dataFields, ViewGroup parent) {
        super(context, dataFields, parent);
    }

    @NonNull
    @Override
    public View getView(int position, ViewGroup parent) {
        DataField dataElement = getItem(position);
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View convertView = inflater.inflate(R.layout.data_field_element, parent, false);
        TextView mCharacterCounter = (TextView) convertView.findViewById(R.id.dataFieldCounter);
        EditText mFieldValue = (EditText) convertView.findViewById(R.id.dataFieldValue);

        DataFieldsTextWatcher textWatcher = new DataFieldsTextWatcher(mFieldValue, mCharacterCounter,
                dataElement);
        mFieldValue.setTag(dataElement.getId());
        mFieldValue.addTextChangedListener(textWatcher);

        mCharacterCounter.setText(String.valueOf(dataElement.getDefaultValue().length()));
        mFieldValue.setText(dataElement.getDefaultValue());
        mFieldValue.setHint(dataElement.getType()); // remade hint
        return convertView;
    }


    private class DataFieldsTextWatcher implements TextWatcher {

        private EditText mView;
        private DataField mValue;
        private TextView mCounterTv;

        private DataFieldsTextWatcher(EditText fieldValueEt, TextView counter, DataField value) {
            this.mView = fieldValueEt;
            this.mValue = value;
            this.mCounterTv = counter;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mValue.setDefaultValue(s.toString());
            mCounterTv.setText(String.valueOf(s.toString().length()));
        }

        public void afterTextChanged(Editable editable) {
            String textData = editable.toString();
            DataFieldsAdapter.this.mFieldValues.put(mView.getTag().toString(), textData);
        }
    }
}
