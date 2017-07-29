package com.icerockdev.babenko.interfaces;

import com.icerockdev.babenko.model.DataField;
import com.icerockdev.babenko.ui.BaseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman Babenko on 06/05/17.
 */

public interface DataFieldsView extends BaseView {
    void hideError();

    void showError();

    void showDataFields(ArrayList<DataField> dataFields);

    void displayFieldsError(List<Integer> errorList);

    void fieldsSuccessfullyChecked();
}
