package com.icerockdev.babenko.ui.home;

import com.icerockdev.babenko.model.DataField;
import com.icerockdev.babenko.ui.ProgressBaseView;

/**
 * Created by Roman Babenko on 06/05/17.
 */

public interface HomeView extends ProgressBaseView {

    void showErrorDialog(int errorCode);

    void showUrlError();

    void gotDataFields(DataField[] fields);

}
