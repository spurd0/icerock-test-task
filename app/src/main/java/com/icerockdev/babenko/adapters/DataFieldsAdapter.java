package com.icerockdev.babenko.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.icerockdev.babenko.R;
import com.icerockdev.babenko.model.DataField;

import java.util.List;

/**
 * Created by Roman Babenko on 01/05/17.
 */

public class DataFieldsAdapter extends ArrayAdapter<DataField> {
    private Context mContext;

    public DataFieldsAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<DataField> objects) {
        super(context, resource, objects);
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.data_field_element, parent, false);
            holder.characterCounter = (TextView) convertView.findViewById(R.id.dataFieldCounter);
            holder.fieldValue = (EditText) convertView.findViewById(R.id.dataFieldValue);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        DataField dataElement = getItem(position);
        holder.fieldValue.setText(dataElement.getDefault_value());
        holder.characterCounter.setText(String.valueOf(dataElement.getDefault_value().length()));
        holder.fieldValue.setHint(dataElement.getType());
        return convertView;
    }

    static class ViewHolder {
        private EditText fieldValue;
        private TextView characterCounter;

    }
}
