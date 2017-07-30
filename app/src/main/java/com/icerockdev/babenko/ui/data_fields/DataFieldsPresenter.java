package com.icerockdev.babenko.ui.data_fields;

import android.os.Parcelable;
import android.support.v4.util.SparseArrayCompat;
import android.widget.EditText;

import com.arellomobile.mvp.InjectViewState;
import com.icerockdev.babenko.managers.impl.DataFieldsManagerImpl;
import com.icerockdev.babenko.managers.interfaces.DataFieldsManager;
import com.icerockdev.babenko.model.DataField;
import com.icerockdev.babenko.ui.BasePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman Babenko on 06/05/17.
 */
@InjectViewState
public class DataFieldsPresenter extends BasePresenter<DataFieldsView> {
    private Parcelable[] mFieldsData;
    private DataFieldsManager mManager;

    public DataFieldsPresenter(Parcelable[] fieldsData, DataFieldsManager manager) { // TODO: 15/05/17 maybe store fieldsData as static member?
        mFieldsData = fieldsData;
        mManager = manager;
    }

    @Override
    public void attachView(DataFieldsView dataFieldsView) {
        super.attachView(dataFieldsView);
        requestFieldsData();
    }

    private void requestFieldsData() {
        ArrayList<DataField> dataFieldsList = mManager.getDataFields(mFieldsData);
        if (getViewState() != null)
            getViewState().showDataFields(dataFieldsList);
    }

    public void submitButtonPressed(SparseArrayCompat<EditText> fieldValues, ArrayList<DataField> dataFields) {
        mManager.checkFields(fieldValues, dataFields,
                new DataFieldsManagerImpl.DataFieldsCheckerCallback() {
                    @Override
                    public void successResponse() {
                        fieldsAreCorrect();
                    }

                    @Override
                    public void failedResponse(List<Integer> errorList) {
                        if (getViewState() != null)
                            getViewState().displayFieldsError(errorList);
                    }
                });
    }

    private void fieldsAreCorrect() {
        if (getViewState() != null)
            getViewState().fieldsSuccessfullyChecked();
    }


}
