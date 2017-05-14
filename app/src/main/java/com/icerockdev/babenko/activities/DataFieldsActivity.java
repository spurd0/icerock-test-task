package com.icerockdev.babenko.activities;

import android.content.Intent;
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
import java.util.List;

/**
 * Created by Roman Babenko on 01/05/17.
 */

public class DataFieldsActivity extends AppCompatActivity implements DataFieldsView {
    private static final String TAG = "DataFieldsActivity";
    public static final String DATA_FIELDS_KEY = "com.icerockdev.babenko.activities.DataFieldsActivity.DATA_FIELDS_KEY";

    private LinearLayout mDataFieldsContainer;
    private TextView mHeaderErrorTv;
    private DataFieldsPresenter mPresenter;
    private DataFieldsAdapter mDataFieldsAdapter;
    private ArrayList<DataField> mDataFields;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_fields);
        mPresenter = new DataFieldsPresenter();
        mPresenter.attachView(this);
        initViews();
        requestFieldsData(); // TODO: 13/05/17 move to presenter
    }

    protected void initViews() {
        mDataFieldsContainer = (LinearLayout) findViewById(R.id.dataFieldsEditTextContainer);
        mHeaderErrorTv = (TextView) findViewById(R.id.validationErrorTv);
        initSubmitButton();
    }

    private void requestFieldsData() {
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
        hideError();
        mPresenter.submitButtonPressed(mDataFieldsAdapter.getFieldValues(), mDataFields);
    }

    public void showError() {
        mHeaderErrorTv.setVisibility(View.VISIBLE);
        mHeaderErrorTv.setText(getText(R.string.data_field_incorrect_format));
        mHeaderErrorTv.requestFocus();
    }

    @Override
    public void gotFieldsData(ArrayList<DataField> dataFields) {
        if (dataFields == null)
            throw new NullPointerException("FieldsListIsNull");
        mDataFields = dataFields;
        mDataFieldsAdapter = new DataFieldsAdapter(this, mDataFields, mDataFieldsContainer);
        mDataFieldsAdapter.attachAdapter(mDataFieldsContainer);
    }

    @Override
    public void displayFieldsError(List<Integer> errorList) {
        showError();
        mDataFieldsAdapter.updateErrorsViews(errorList);
    }

    @Override
    public void fieldsSuccessfullyChecked() {
        Intent intent = new Intent(this, ImagesActivity.class);
        startActivity(intent);
    }

    public void hideError() {
        mHeaderErrorTv.setVisibility(View.GONE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.detachView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.attachView(this);
    }
}
