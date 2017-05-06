package com.icerockdev.babenko.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icerockdev.babenko.R;
import com.icerockdev.babenko.adapters.DataFieldsAdapter;
import com.icerockdev.babenko.interfaces.DataFieldsView;
import com.icerockdev.babenko.model.DataField;
import com.icerockdev.babenko.presenters.DataFieldsPresenter;

import java.util.ArrayList;

/**
 * Created by Roman Babenko on 01/05/17.
 */

public class DataFieldsActivity extends AppCompatActivity implements DataFieldsView {
    private static final String TAG = "DataFieldsActivity";
    public static final String DATA_FIELDS_KEY = "com.icerockdev.babenko.activities.DataFieldsActivity.DATA_FIELDS_KEY";

    private LinearLayout mDataFieldsContainer;
    private TextView mHeaderErrorTv;
    private DataFieldsPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_fields);
        mPresenter = new DataFieldsPresenter();
        initViews();
        requestFieldsData();
    }

    protected void initViews() {
        mDataFieldsContainer = (LinearLayout) findViewById(R.id.dataFieldsEditTextContainer);
        mHeaderErrorTv = (TextView) findViewById(R.id.validationErrorTv);
        initSubmitButton();
    }

    private void requestFieldsData(){
        mPresenter.requestFieldsData(this);
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

    public void showError(String error) {
        mHeaderErrorTv.setVisibility(View.VISIBLE);
        mHeaderErrorTv.setText("Test");
    }

    @Override
    public void gotFieldsData(ArrayList<DataField> dataFields) {
        if (dataFields == null)
            throw new NullPointerException("FieldsListIsNull");
        DataFieldsAdapter adapter = new DataFieldsAdapter(this, dataFields, mDataFieldsContainer);
        adapter.attachAdapter(mDataFieldsContainer);
    }

    public void hideError() {
        mHeaderErrorTv.setVisibility(View.GONE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // TODO: 06/05/17 check for errors & add base activity.
        mPresenter.attachView(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.detachView();
    }
}
