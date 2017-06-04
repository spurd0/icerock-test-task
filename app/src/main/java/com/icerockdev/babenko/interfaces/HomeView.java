package com.icerockdev.babenko.interfaces;

import com.icerockdev.babenko.model.DataField;

/**
 * Created by Roman Babenko on 06/05/17.
 */

public interface HomeView extends ProgressBaseView {

    void showErrorDialog(int errorCode);

    void showUrlError();

    void gotDataFields(DataField[] fields);

}
