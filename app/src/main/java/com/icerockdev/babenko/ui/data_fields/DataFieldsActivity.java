package com.icerockdev.babenko.ui.data_fields;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.model.entities.DataField;
import com.icerockdev.babenko.ui.base.activities.BaseActivity;
import com.icerockdev.babenko.ui.data_fields.adapters.DataFieldsAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.icerockdev.babenko.core.ApplicationConstants.EMAIL;
import static com.icerockdev.babenko.core.ApplicationConstants.NUMBER;
import static com.icerockdev.babenko.core.ApplicationConstants.PHONE;
import static com.icerockdev.babenko.core.ApplicationConstants.TEXT;
import static com.icerockdev.babenko.core.ApplicationConstants.URL;

/**
 * Created by Roman Babenko on 01/05/17.
 */

public class DataFieldsActivity extends BaseActivity implements DataFieldsView {
    public static final String DATA_FIELDS_KEY = "DataFieldsActivity.DATA_FIELDS_KEY";
    private static final String TAG = "DataFieldsActivity";

    @InjectPresenter
    DataFieldsPresenter mPresenter;

    @BindView(R.id.validationErrorTv)
    TextView validationErrorTv;
    @BindView(R.id.submitFieldsButton)
    AppCompatButton submitFieldsButton;

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

        ButterKnife.bind(this);
    }

    @ProvidePresenter
    DataFieldsPresenter provideDataFieldsPresenter() {
        Parcelable[] allParcelables = getIntent().getParcelableArrayExtra(DATA_FIELDS_KEY);
        DataField[] dataFields = new DataField[allParcelables.length];
        for (int i = 0; i < allParcelables.length; i++) {
            dataFields[i] = (DataField) allParcelables[i];
        }
        return new DataFieldsPresenter(new DataFieldsInteractorImpl(dataFields));
    }


    public void showError() {
        validationErrorTv.setVisibility(View.VISIBLE);
        validationErrorTv.setText(getText(R.string.data_field_incorrect_format));
        validationErrorTv.requestFocus();
    }

    @Override
    public void showDataFields(List<DataField> dataFields) {
        if (BuildConfig.DEBUG) {
            fillDataFields(dataFields);
        }

        mDataFieldsAdapter = new DataFieldsAdapter(this, dataFields);
        // hope that it won`t be too much fields, moved to recyclerview @see feature/recycler_view_datafields
        mDataFieldsAdapter.attachAdapter((LinearLayout) findViewById(R.id.dataFieldsEditTextContainer));

        submitFieldsButton.setEnabled(true);
    }

    private void fillDataFields(List<DataField> dataFields) {// TODO: 15/05/17 how to correctly make a testing or skip it?
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
        navigator.navigateToImagesActivity(this);
        finish();
    }

    public void hideError() {
        validationErrorTv.setVisibility(View.GONE);
    }

    public void submitFieldsClicked(View view) {
        hideError();
        mPresenter.submitButtonPressed(mDataFieldsAdapter.getFieldValues());
    }
}
