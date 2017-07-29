package com.icerockdev.babenko.ui.data_fields;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.ui.data_fields.adapters.DataFieldsAdapter;
import com.icerockdev.babenko.ui.images.ImagesActivity;
import com.icerockdev.babenko.interfaces.DataFieldsView;
import com.icerockdev.babenko.managers.impl.DataFieldsManagerImpl;
import com.icerockdev.babenko.model.DataField;

import java.util.ArrayList;
import java.util.List;

import static com.icerockdev.babenko.core.ApplicationConstants.EMAIL;
import static com.icerockdev.babenko.core.ApplicationConstants.NUMBER;
import static com.icerockdev.babenko.core.ApplicationConstants.PHONE;
import static com.icerockdev.babenko.core.ApplicationConstants.TEXT;
import static com.icerockdev.babenko.core.ApplicationConstants.URL;

/**
 * Created by Roman Babenko on 01/05/17.
 */

public class DataFieldsActivity extends AppCompatActivity implements DataFieldsView {
    public static final String DATA_FIELDS_KEY = "com.icerockdev.babenko.ui.data_fields.DataFieldsActivity.DATA_FIELDS_KEY";
    private static final String TAG = "DataFieldsActivity";
    private TextView mHeaderErrorTv;
    @InjectPresenter
    DataFieldsPresenter mPresenter;
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
        initViews();
    }

    @ProvidePresenter
    DataFieldsPresenter provideDataFieldsPresenter() {
        return new DataFieldsPresenter(getIntent().getParcelableArrayExtra(DATA_FIELDS_KEY),
               new DataFieldsManagerImpl());
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
        // hope that it won`t be too much fields, moved to recyclerview @see feature/recycler_view_datafields
        mDataFieldsAdapter.attachAdapter((LinearLayout) findViewById(R.id.dataFieldsEditTextContainer));
        initSubmitButton(dataFields);
    }

    private void fillDataFields(ArrayList<DataField> dataFields) {// TODO: 15/05/17 how to correctly make a testing or skip it?
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
