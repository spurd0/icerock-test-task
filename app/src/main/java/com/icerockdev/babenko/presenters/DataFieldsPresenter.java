package com.icerockdev.babenko.presenters;

import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.util.SparseArrayCompat;
import android.widget.EditText;

import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.interfaces.DataFieldsView;
import com.icerockdev.babenko.managers.DataFieldsManager;
import com.icerockdev.babenko.model.DataField;

import java.util.ArrayList;
import java.util.List;

import static com.icerockdev.babenko.activities.DataFieldsActivity.DATA_FIELDS_KEY;

/**
 * Created by Roman Babenko on 06/05/17.
 */

public class DataFieldsPresenter extends BasePresenter<DataFieldsView> {

    public void requestFieldsData(Activity activity) {
        Parcelable[] data = activity.getIntent().getParcelableArrayExtra(DATA_FIELDS_KEY);
        ArrayList<DataField> dataFieldsList = new ArrayList<>();
        for (Parcelable aData : data) {
            dataFieldsList.add((DataField) aData);
        }
        if (getView() != null)
            getView().gotFieldsData(dataFieldsList);
    }

    public void submitButtonPressed(SparseArrayCompat<EditText> fieldValues, ArrayList<DataField> dataFields) {
        IceRockApplication.getInstance().getDataFieldsManager().checkFields(fieldValues, dataFields,
                new DataFieldsManager.DataFieldsCheckerCallback() {
                    @Override
                    public void successResponse() {
                        fieldsAreCorrect();
                    }

                    @Override
                    public void failedResponse(List<Integer> errorList) {
                        if (getView() != null)
                            getView().displayFieldsError(errorList);
                    }
                });
    }

    private void fieldsAreCorrect() {
        if (getView() != null)
            getView().fieldsSuccessfullyChecked();
    }


}
