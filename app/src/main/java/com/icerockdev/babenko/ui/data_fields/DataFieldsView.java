package com.icerockdev.babenko.ui.data_fields;

import com.icerockdev.babenko.model.entities.DataField;
import com.icerockdev.babenko.ui.base.views.BaseView;

import java.util.List;

/**
 * Created by Roman Babenko on 06/05/17.
 */

public interface DataFieldsView extends BaseView {
    void hideError();

    void showError();

    void showDataFields(List<DataField> dataFields);

    void displayFieldsError(List<Integer> errorList);

    void fieldsSuccessfullyChecked();
}
