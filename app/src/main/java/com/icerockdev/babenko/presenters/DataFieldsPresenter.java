package com.icerockdev.babenko.presenters;

import android.os.Parcelable;
import android.support.v4.util.SparseArrayCompat;
import android.widget.EditText;

import com.icerockdev.babenko.interfaces.DataFieldsView;
import com.icerockdev.babenko.managers.DataFieldsManager;
import com.icerockdev.babenko.model.DataField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman Babenko on 06/05/17.
 */

public class DataFieldsPresenter extends BasePresenter<DataFieldsView> {
    private Parcelable[] mFieldsData;
    private DataFieldsManager mManager;

    public DataFieldsPresenter(Parcelable[] fieldsData) { // TODO: 15/05/17 maybe store fieldsData as static member?
        mFieldsData = fieldsData;
        mManager = new DataFieldsManager();
    }

    @Override
    public void attachView(DataFieldsView dataFieldsView) {
        super.attachView(dataFieldsView);
        requestFieldsData();
    }

    private void requestFieldsData() {
        ArrayList<DataField> dataFieldsList = mManager.getDataFields(mFieldsData);
        if (getView() != null)
            getView().showDataFields(dataFieldsList);
    }

    public void submitButtonPressed(SparseArrayCompat<EditText> fieldValues, ArrayList<DataField> dataFields) {
        mManager.checkFields(fieldValues, dataFields,
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
