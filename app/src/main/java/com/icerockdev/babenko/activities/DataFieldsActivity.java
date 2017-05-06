package com.icerockdev.babenko.activities;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.adapters.DataFieldsAdapter;
import com.icerockdev.babenko.model.DataField;
import com.icerockdev.babenko.utils.UtilsHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Roman Babenko on 01/05/17.
 */

public class DataFieldsActivity extends AppCompatActivity {
    private static final String TAG = "DataFieldsActivity";
    public static final String DATA_FIELDS_KEY = "com.icerockdev.babenko.activities.DataFieldsActivity.DATA_FIELDS_KEY";

    private ArrayList<DataField> mDataFieldsList;
    private DataFieldsAdapter mFieldsAdapter;
    private ListView mFieldsLv; // TODO: 06/05/17 MOVE TO ViewGroup 
    private TextView mHeaderErrorTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_fields);
        getIntentData();
        initViews();
    }

    protected void initViews() {
        initListView();
    }

    private void initListView() throws NullPointerException {
        mFieldsLv = (ListView) findViewById(R.id.dataFieldsListView);
        if (mDataFieldsList == null)
            throw new NullPointerException("FieldsListIsNull");
        mFieldsAdapter = new DataFieldsAdapter(this, R.layout.data_field_element, mDataFieldsList);
        mFieldsLv.setAdapter(mFieldsAdapter);
        addFooterToListView();
        addHeaderToListView();
    }

    private void addFooterToListView() {
        LinearLayout footer = new LinearLayout(this);
        Button submitButton = new Button(this);
        submitButton.setText(getString(R.string.submit_fields_button_text));
        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout
                .LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        buttonLayoutParams.setMargins(UtilsHelper.convertDpToPx(this, getResources()
                .getDimension(R.dimen.data_field_element_footer_margin_horizontal)),
                UtilsHelper.convertDpToPx(this, getResources()
                        .getDimension(R.dimen.data_field_element_footer_margin_vertical)),
                UtilsHelper.convertDpToPx(this, getResources()
                        .getDimension(R.dimen.data_field_element_footer_margin_horizontal)),
                UtilsHelper.convertDpToPx(this, getResources()
                        .getDimension(R.dimen.data_field_element_footer_margin_vertical)));
        submitButton.setLayoutParams(buttonLayoutParams);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitButtonPressed();
            }
        });
        footer.addView(submitButton);
        mFieldsLv.addFooterView(footer);
    }

    private void addHeaderToListView() {
        RelativeLayout headerLayout = new RelativeLayout(this);
        mHeaderErrorTv = new TextView(this);
        mHeaderErrorTv.setText(getString(R.string.data_field_incorrect_format));
        RelativeLayout.LayoutParams errorTvLayoutParams = new RelativeLayout
                .LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        errorTvLayoutParams.setMargins(UtilsHelper.convertDpToPx(this, getResources()
                        .getDimension(R.dimen.data_field_element_header_margin_horizontal)),
                UtilsHelper.convertDpToPx(this, getResources()
                        .getDimension(R.dimen.data_field_element_header_margin_vertical)),
                UtilsHelper.convertDpToPx(this, getResources()
                        .getDimension(R.dimen.data_field_element_header_margin_horizontal)),
                UtilsHelper.convertDpToPx(this, getResources()
                        .getDimension(R.dimen.data_field_element_header_margin_vertical)));
        errorTvLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
        errorTvLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        mHeaderErrorTv.setLayoutParams(errorTvLayoutParams);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mHeaderErrorTv.setTextColor(getColor(R.color.errorTextColor));
        } else {
            mHeaderErrorTv.setTextColor(getResources().getColor(R.color.errorTextColor));
        }
        headerLayout.addView(mHeaderErrorTv);
        mHeaderErrorTv.setVisibility(View.GONE);
        mFieldsLv.addHeaderView(headerLayout);
    }

    private void submitButtonPressed() {
        HashMap<String, String> fieldsValues = mFieldsAdapter.getFieldValues();
        for (Map.Entry<String, String> value : fieldsValues.entrySet())
            Log.d(TAG, value.getValue());
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
