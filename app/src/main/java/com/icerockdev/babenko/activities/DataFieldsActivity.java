package com.icerockdev.babenko.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.adapters.DataFieldsAdapter;
import com.icerockdev.babenko.interfaces.DataFieldsView;
import com.icerockdev.babenko.model.DataField;
import com.icerockdev.babenko.presenters.DataFieldsPresenter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.icerockdev.babenko.data.ApplicationConstants.EMAIL;
import static com.icerockdev.babenko.data.ApplicationConstants.NUMBER;
import static com.icerockdev.babenko.data.ApplicationConstants.PHONE;
import static com.icerockdev.babenko.data.ApplicationConstants.TEXT;
import static com.icerockdev.babenko.data.ApplicationConstants.URL;

/**
 * Created by Roman Babenko on 01/05/17.
 */

public class DataFieldsActivity extends AppCompatActivity implements DataFieldsView {
    private static final String TAG = "DataFieldsActivity";
    public static final String DATA_FIELDS_KEY = "com.icerockdev.babenko.activities.DataFieldsActivity.DATA_FIELDS_KEY";

    private TextView mHeaderErrorTv;
    private DataFieldsPresenter mPresenter;
    private DataFieldsAdapter mDataFieldsAdapter;

    public static void startActivity(Context context, DataField[] data) {
        Intent dataFieldsIntent = new Intent(context, DataFieldsActivity.class);
        dataFieldsIntent.putExtra(DATA_FIELDS_KEY, data);
        context.startActivity(dataFieldsIntent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_fields);
        mPresenter = new DataFieldsPresenter(getIntent().getParcelableArrayExtra(DATA_FIELDS_KEY));
        initViews();
    }

    protected void initViews() {
        mHeaderErrorTv = (TextView) findViewById(R.id.validationErrorTv);
    }

    private void initSubmitButton(final ArrayList<DataField> dataFields) {
        Button submitButton = (Button) findViewById(R.id.submitFieldsButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideError();
                mPresenter.submitButtonPressed(mDataFieldsAdapter.getFieldValues(), dataFields);
            }
        });
    }

    public void showError() {
        mHeaderErrorTv.setVisibility(View.VISIBLE);
        mHeaderErrorTv.setText(getText(R.string.data_field_incorrect_format));
        mHeaderErrorTv.requestFocus();
    }

    @Override
    public void showDataFields(ArrayList<DataField> dataFields) {
        if (mDataFieldsAdapter != null)
            return;
        if (dataFields == null)
            throw new NullPointerException("FieldsListIsNull");
        if (BuildConfig.DEBUG)
            fillDataFields(dataFields);
        mDataFieldsAdapter = new DataFieldsAdapter(this, dataFields);
        mDataFieldsAdapter.attachAdapter((LinearLayout) findViewById(R.id.dataFieldsEditTextContainer));
        initSubmitButton(dataFields);
    }

    private void fillDataFields(ArrayList<DataField> dataFields) {
        for (DataField dataField : dataFields) {
            switch (dataField.getType()) {
                case TEXT:
                    dataField.setValue("Very-very-very long text");
                    break;
                case EMAIL:
                    dataField.setValue("foo@java.com");
                    break;
                case PHONE:
                    dataField.setValue("+79991234200");
                    break;
                case NUMBER:
                    dataField.setValue("12345");
                    break;
                case URL:
                    dataField.setValue("ya.ru");
                    break;
            }
        }
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
    protected void onPause() {
        mPresenter.detachView();
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.attachView(this);
    }
}
