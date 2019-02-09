package com.icerockdev.babenko.ui.data_fields

import android.support.v4.util.SparseArrayCompat
import android.widget.EditText

import com.arellomobile.mvp.InjectViewState
import com.icerockdev.babenko.applicaiton.utils.applyIoMainThreadSchedulersToSingle
import com.icerockdev.babenko.model.entities.DataField
import com.icerockdev.babenko.ui.base.BasePresenter

/**
 * Created by Roman Babenko on 06/05/17.
 */
@InjectViewState
class DataFieldsPresenter(private val mDataFieldsInteractor: DataFieldsInteractor)// TODO: 15/05/17 maybe store fieldsData as static member?
    : BasePresenter<DataFieldsView>() {

    public override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        requestFieldsData()
    }

    private fun requestFieldsData() {
        mDataFieldsInteractor.requestDataFields()
            .compose<List<DataField>>(applyIoMainThreadSchedulersToSingle<List<DataField>>())
            .subscribe { dataFields -> viewState.showDataFields(dataFields) }
    }

    fun submitButtonPressed(fieldValues: SparseArrayCompat<EditText>) {
        // TODO: 11/12/2017 show progress dialog
        mDataFieldsInteractor.checkFields(fieldValues)
            .compose(applyIoMainThreadSchedulersToSingle())
            .subscribe { integers ->
                if (integers.isEmpty()) {
                    fieldsAreCorrect()
                } else {
                    viewState.displayFieldsError(integers)
                }
            }
    }

    private fun fieldsAreCorrect() {
        viewState.fieldsSuccessfullyChecked()
    }

}
