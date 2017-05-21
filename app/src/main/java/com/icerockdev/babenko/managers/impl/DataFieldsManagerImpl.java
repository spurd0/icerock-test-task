package com.icerockdev.babenko.managers.impl;

import android.os.Parcelable;
import android.support.v4.util.SparseArrayCompat;
import android.util.Patterns;
import android.widget.EditText;

import com.icerockdev.babenko.managers.interfaces.DataFieldsManager;
import com.icerockdev.babenko.model.DataField;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.icerockdev.babenko.data.ApplicationConstants.EMAIL;
import static com.icerockdev.babenko.data.ApplicationConstants.NUMBER;
import static com.icerockdev.babenko.data.ApplicationConstants.PHONE;
import static com.icerockdev.babenko.data.ApplicationConstants.TEXT;
import static com.icerockdev.babenko.data.ApplicationConstants.URL;

/**
 * Created by Roman Babenko on 30/04/17.
 */

public class DataFieldsManagerImpl implements DataFieldsManager {

    private ArrayList<DataField> mDataFieldsList;

    public void checkFields(SparseArrayCompat<EditText> fieldValues, ArrayList<DataField> dataFields,
                            DataFieldsCheckerCallback callback) {
        List<Integer> errorList = new ArrayList<Integer>();
        for (int i = 0; i < fieldValues.size(); i++) {
            int key = fieldValues.keyAt(i);
            EditText fieldValue = fieldValues.get(key);
            for (DataField dataField : dataFields)
                if (dataField.getId() == key) {
                    if (!isDataFieldCorrect(fieldValue.getText().toString(), dataField.getType()))
                        errorList.add(key);
                }
        }
        if (errorList.isEmpty())
            callback.successResponse();
        else callback.failedResponse(errorList);
    }

    public boolean isDataFieldCorrect(String data, String type) {
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

    public ArrayList<DataField> getDataFields(Parcelable[] mFieldsData) {
        if (mDataFieldsList == null) {
            mDataFieldsList = new ArrayList<>();
            for (Parcelable aData : mFieldsData) {
                mDataFieldsList.add((DataField) aData);
            }
        }
        return mDataFieldsList;
    }

}
