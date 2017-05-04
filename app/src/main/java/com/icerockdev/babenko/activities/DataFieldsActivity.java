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

/**
 * Created by Roman Babenko on 01/05/17.
 */

public class DataFieldsActivity extends AppCompatActivity {
    private static final String TAG = "DataFieldsActivity";
    public static final String DATA_FIELDS_KEY = "com.icerockdev.babenko.activities.DataFieldsActivity.DATA_FIELDS_KEY";

    private ArrayList<DataField> mDataFieldsList;
    private ListView mFieldsLV;
    private RelativeLayout mHeader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_fields);
        getIntentData();
        initViews();
    }

    protected void initViews() {
        mFieldsLV = (ListView) findViewById(R.id.dataFieldsListView);
        initListView();
    }

    private void initListView() throws NullPointerException {
        if (mDataFieldsList == null)
            throw new NullPointerException("FieldsListIsNull");
        DataFieldsAdapter adapter = new DataFieldsAdapter(this, R.layout.data_field_element, mDataFieldsList);
        mFieldsLV.setAdapter(adapter);
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
        footer.addView(submitButton);
        mFieldsLV.addFooterView(footer);
    }

    private void addHeaderToListView() {
        mHeader = new RelativeLayout(this);
        TextView errorTv = new TextView(this);
        errorTv.setText(getString(R.string.data_field_incorrect_format));
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
        errorTv.setLayoutParams(errorTvLayoutParams);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            errorTv.setTextColor(getColor(R.color.errorTextColor));
        } else {
            errorTv.setTextColor(getResources().getColor(R.color.errorTextColor));
        }
        mHeader.addView(errorTv);
        mHeader.setVisibility(View.GONE);
        mFieldsLV.addHeaderView(mHeader);
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
