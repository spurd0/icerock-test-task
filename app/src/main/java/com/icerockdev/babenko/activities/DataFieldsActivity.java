package com.icerockdev.babenko.activities;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.model.DataField;

/**
 * Created by Roman Babenko on 01/05/17.
 */

public class DataFieldsActivity extends AppCompatActivity {
    private static final String TAG = "DataFieldsActivity";
    public static final String DATA_FIELDS_KEY = "com.icerockdev.babenko.activities.DataFieldsActivity.DATA_FIELDS_KEY";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_fields);
        Parcelable[] data = getIntent().getParcelableArrayExtra(DATA_FIELDS_KEY);
        DataField[] dataFields = new DataField[data.length];
        for (int i = 0 ; i < data.length; i++) {
            dataFields[i] = (DataField)data[i];
        }
        if (BuildConfig.DEBUG)
            Log.d(TAG, "DataFields length is " + dataFields.length);
    }
}
