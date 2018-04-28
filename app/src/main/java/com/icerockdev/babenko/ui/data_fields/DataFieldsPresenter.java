package com.icerockdev.babenko.ui.data_fields;

import android.support.v4.util.SparseArrayCompat;
import android.widget.EditText;

import com.arellomobile.mvp.InjectViewState;
import com.icerockdev.babenko.ui.base.BasePresenter;
import com.icerockdev.babenko.utils.RxUtils;

/**
 * Created by Roman Babenko on 06/05/17.
 */
@InjectViewState
public class DataFieldsPresenter extends BasePresenter<DataFieldsView> {
    private DataFieldsInteractor mDataFieldsInteractor;

    public DataFieldsPresenter(DataFieldsInteractor dataFieldsInteractor) { // TODO: 15/05/17 maybe store fieldsData as static member?
        mDataFieldsInteractor = dataFieldsInteractor;
    }

    @Override
    public void onFirstViewAttach() {
        super.onFirstViewAttach();
        requestFieldsData();
    }

    private void requestFieldsData() {
        mDataFieldsInteractor.requestDataFields()
                .compose(RxUtils.applyIoMainThreadSchedulersToSingle())
                .subscribe(dataFields -> getViewState().showDataFields(dataFields));
    }

    public void submitButtonPressed(SparseArrayCompat<EditText> fieldValues) {
        // TODO: 11/12/2017 show progress dialog
        mDataFieldsInteractor.checkFields(fieldValues)
                .compose(RxUtils.applyIoMainThreadSchedulersToSingle())
                .subscribe(integers -> {
                    if (integers.isEmpty()) {
                        fieldsAreCorrect();
                    } else {
                        getViewState().displayFieldsError(integers);
                    }
                });
    }

    private void fieldsAreCorrect() {
        getViewState().fieldsSuccessfullyChecked();
    }

}
