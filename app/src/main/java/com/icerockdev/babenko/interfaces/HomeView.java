package com.icerockdev.babenko.interfaces;

import com.icerockdev.babenko.model.DataField;

/**
 * Created by Roman Babenko on 06/05/17.
 */

public interface HomeView extends ProgressBaseView {

    void showErrorDialog(String message);

    void showUrlError(String error);

    void gotDataFields(DataField[] fields);

}
