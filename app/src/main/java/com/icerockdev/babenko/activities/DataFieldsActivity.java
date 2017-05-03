package com.icerockdev.babenko.activities;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.adapters.DataFieldsAdapter;
import com.icerockdev.babenko.model.DataField;

import java.util.ArrayList;

/**
 * Created by Roman Babenko on 01/05/17.
 */

public class DataFieldsActivity extends AppCompatActivity {
    private static final String TAG = "DataFieldsActivity";
    public static final String DATA_FIELDS_KEY = "com.icerockdev.babenko.activities.DataFieldsActivity.DATA_FIELDS_KEY";

    private ArrayList<DataField> mDataFieldsList;
    private ListView mFieldsLV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_fields);
        initViews();
        getIntentData();
        initListView();
    }

    protected void initViews() {
        mFieldsLV = (ListView) findViewById(R.id.dataFieldsListView);
    }

    private void initListView() {
        DataFieldsAdapter adapter = new DataFieldsAdapter(this, R.layout.data_field_element, mDataFieldsList);
        mFieldsLV.setAdapter(adapter);
        mFieldsLV.addFooterView(new Button(this));
    }

    private void getIntentData() {
        Parcelable[] data = getIntent().getParcelableArrayExtra(DATA_FIELDS_KEY);
        mDataFieldsList = new ArrayList<>();
        for (Parcelable aData : data) {
            mDataFieldsList.add((DataField) aData);
        }
        if (BuildConfig.DEBUG)
            Log.d(TAG, "DataFields length is " + mDataFieldsList.size());
    }
}
