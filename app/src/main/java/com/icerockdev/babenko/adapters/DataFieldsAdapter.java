package com.icerockdev.babenko.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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

    public DataFieldsAdapter(@NonNull Context context, @NonNull List<DataField> dataFields) {
        super(context, dataFields);
    }

    @NonNull
    @Override
    public View getView(int position) {
        DataField dataElement = getItem(position);
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View convertView = inflater.inflate(R.layout.data_field_element, null, false);
        TextView mCharacterCounter = (TextView) convertView.findViewById(R.id.dataFieldCounter);
        EditText mFieldValue = (EditText) convertView.findViewById(R.id.dataFieldValue);

        DataFieldsTextWatcher textWatcher = new DataFieldsTextWatcher(mFieldValue, dataElement.getDefault_value());
        mFieldValue.setTag(dataElement.getId());
        mFieldValue.addTextChangedListener(textWatcher);

        mCharacterCounter.setText(String.valueOf(dataElement.getDefault_value().length()));
        mFieldValue.setText(dataElement.getDefault_value());
        mFieldValue.setHint(dataElement.getType()); // remade hint
        return convertView;
    }


    private class DataFieldsTextWatcher implements TextWatcher {

        private View mView;
        private String mValue;

        private DataFieldsTextWatcher(View view, String value) {
            this.mView = view;
            this.mValue = value;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mValue = s.toString();
        }

        public void afterTextChanged(Editable editable) {
            String textData = editable.toString();
            DataFieldsAdapter.this.mFieldValues.put(mView.getTag().toString(), textData);
        }
    }
}
