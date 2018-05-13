package com.icerockdev.babenko.ui.data_fields

import com.icerockdev.babenko.model.entities.DataField
import com.icerockdev.babenko.ui.base.views.BaseView

/**
 * Created by Roman Babenko on 06/05/17.
 */

interface DataFieldsView : BaseView {
    fun hideError()

    fun showError()

    fun showDataFields(dataFields: List<DataField>)

    fun displayFieldsError(errorList: List<Int>)

    fun fieldsSuccessfullyChecked()
}
