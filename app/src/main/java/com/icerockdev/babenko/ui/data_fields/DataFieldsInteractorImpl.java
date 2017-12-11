package com.icerockdev.babenko.ui.data_fields;

import android.support.annotation.NonNull;
import android.support.v4.util.SparseArrayCompat;
import android.util.Patterns;
import android.widget.EditText;

import com.icerockdev.babenko.model.entities.DataField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import io.reactivex.Single;

import static com.icerockdev.babenko.core.ApplicationConstants.EMAIL;
import static com.icerockdev.babenko.core.ApplicationConstants.NUMBER;
import static com.icerockdev.babenko.core.ApplicationConstants.PHONE;
import static com.icerockdev.babenko.core.ApplicationConstants.TEXT;
import static com.icerockdev.babenko.core.ApplicationConstants.URL;

/**
 * Created by Roman Babenko on 30/04/17.
 */

public class DataFieldsInteractorImpl implements DataFieldsInteractor {

    private DataField[] mDataFields;

    public DataFieldsInteractorImpl(@NonNull DataField[] dataFields) {
        mDataFields = dataFields;
    }

    public Single<List<Integer>> checkFields(SparseArrayCompat<EditText> fieldValues) {
        return Single.fromCallable(() -> {
            List<Integer> errorList = new ArrayList<Integer>();
            for (int i = 0; i < fieldValues.size(); i++) {
                int key = fieldValues.keyAt(i);
                EditText fieldValue = fieldValues.get(key);
                for (DataField dataField : mDataFields)
                    if (dataField.getId() == key) {
                        if (!isDataFieldCorrect(fieldValue.getText().toString(), dataField.getType()))
                            errorList.add(key);
                    }
            }
            return errorList;
        });
    }

    private boolean isDataFieldCorrect(String data, String type) {
        if (data.isEmpty())
            return false;
        switch (type) {
            case TEXT:
                int length = data.length();
                return (length > 10 && length < 30);
            case EMAIL:
                return android.util.Patterns.EMAIL_ADDRESS.matcher(data).matches();
            case PHONE:
                Pattern phonePattern = Pattern.compile("^[+7]{2}\\d{10}$");
                return phonePattern.matcher(data).matches();
            case NUMBER:
                Pattern numberPattern = Pattern.compile("^\\d{1,5}$");
                return numberPattern.matcher(data).matches();
            case URL:
                return Patterns.WEB_URL.matcher(data).matches();
            default:
                throw new IllegalArgumentException("Unknown type");
        }
    }

    public Single<List<DataField>> requestDataFields() {
        return Single.fromCallable(() -> Arrays.asList(mDataFields));
    }
}
