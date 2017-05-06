package com.icerockdev.babenko.activities;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.adapters.DataFieldsAdapter;
import com.icerockdev.babenko.model.DataField;
import com.icerockdev.babenko.utils.UtilsHelper;

import java.util.ArrayList;

/**
 * Created by Roman Babenko on 01/05/17.
 */

public class DataFieldsActivity extends AppCompatActivity {
    private static final String TAG = "DataFieldsActivity";
    public static final String DATA_FIELDS_KEY = "com.icerockdev.babenko.activities.DataFieldsActivity.DATA_FIELDS_KEY";

    private LinearLayout mDataFieldsContainer;
    private TextView mHeaderErrorTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_fields);
        getIntentData();
        initViews();
    }

    protected void initViews() {
        mDataFieldsContainer = (LinearLayout) findViewById(R.id.dataFieldsEditTextContainer);
        mHeaderErrorTv = (TextView) findViewById(R.id.validationErrorTv);
        initDataFieldsEt();
        initSubmitButton();
    }

    private void initDataFieldsEt() throws NullPointerException {
        ArrayList<DataField> dataFieldsList = getIntentData();
        if (dataFieldsList == null)
            throw new NullPointerException("FieldsListIsNull");
        DataFieldsAdapter adapter = new DataFieldsAdapter(this, dataFieldsList, mDataFieldsContainer);
        adapter.attachAdapter(mDataFieldsContainer);
    }

    private void initSubmitButton() {
        Button submitButton = (Button) findViewById(R.id.submitFieldsButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitButtonPressed();
            }
        });
    }

    private void submitButtonPressed() {
    }

    private ArrayList<DataField> getIntentData() {
        Parcelable[] data = getIntent().getParcelableArrayExtra(DATA_FIELDS_KEY);
        ArrayList<DataField> dataFieldsList = new ArrayList<>();
        for (Parcelable aData : data) {
            dataFieldsList.add((DataField) aData);
        }
        if (BuildConfig.DEBUG)
            Log.d(TAG, "DataFields length is " + dataFieldsList.size());
        return dataFieldsList;
    }

    private void showError(String error) {
        mHeaderErrorTv.setVisibility(View.VISIBLE);
        mHeaderErrorTv.setText("Test");
    }

    private void hideError() {
        mHeaderErrorTv.setVisibility(View.GONE);
    }
}
