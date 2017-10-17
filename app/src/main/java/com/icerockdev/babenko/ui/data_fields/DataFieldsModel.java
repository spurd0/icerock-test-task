package com.icerockdev.babenko.ui.data_fields;

import android.os.Parcelable;
import android.support.v4.util.SparseArrayCompat;
import android.widget.EditText;

import com.icerockdev.babenko.model.entities.DataField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman Babenko on 30/04/17.
 */

public interface DataFieldsModel {

    void checkFields(SparseArrayCompat<EditText> fieldValues, ArrayList<DataField> dataFields,
                     DataFieldsCheckerCallback callback);

    boolean isDataFieldCorrect(String data, String type);

    ArrayList<DataField> getDataFields(Parcelable[] mFieldsData);

    interface DataFieldsCheckerCallback {
        void successResponse();

        void failedResponse(List<Integer> errorList);
    }

}
