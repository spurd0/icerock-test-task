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

public class DataFieldsAdapter extends ArrayAdapter<DataField> {
    private Context mContext;
    private HashMap<String, String> mFieldValues = new HashMap<String, String>();

    public DataFieldsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<DataField> objects) {
        super(context, resource, objects);
        mContext = context;
    }

    public HashMap<String, String> getFieldValues() {
        return mFieldValues;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = null;
        DataField dataElement = getItem(position);
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.data_field_element, parent, false);
            holder.mCharacterCounter = (TextView) convertView.findViewById(R.id.dataFieldCounter);
            holder.mFieldValue = (EditText) convertView.findViewById(R.id.dataFieldValue);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (holder.mTextWatcher != null)
            holder.mFieldValue.removeTextChangedListener(holder.mTextWatcher);
        holder.mTextWatcher = new DataFieldsTextWatcher(holder.mFieldValue, dataElement.getDefault_value());
        holder.mFieldValue.setTag(dataElement.getId());
        holder.mFieldValue.addTextChangedListener(holder.mTextWatcher);

        holder.mCharacterCounter.setText(String.valueOf(dataElement.getDefault_value().length()));
        holder.mFieldValue.setText(dataElement.getDefault_value());
        holder.mFieldValue.setHint(dataElement.getType()); // remade hint
        return convertView;
    }

    private static class ViewHolder {
        private EditText mFieldValue;
        private TextView mCharacterCounter;
        private TextWatcher mTextWatcher;
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
