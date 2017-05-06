package com.icerockdev.babenko.interfaces;

import com.icerockdev.babenko.model.DataField;

import java.util.ArrayList;

/**
 * Created by Roman Babenko on 06/05/17.
 */

public interface DataFieldsView extends BaseView {
    void hideError();

    void showError(String error);

    void gotFieldsData(ArrayList<DataField> dataFields);
}
