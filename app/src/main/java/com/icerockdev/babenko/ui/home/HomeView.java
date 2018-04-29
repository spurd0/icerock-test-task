package com.icerockdev.babenko.ui.home;

import com.icerockdev.babenko.model.entities.DataField;
import com.icerockdev.babenko.ui.base.views.ProgressBaseView;

/**
 * Created by Roman Babenko on 06/05/17.
 */

public interface HomeView extends ProgressBaseView {
    void showErrorDialog();

    void showUrlError();

    void gotDataFields(DataField[] fields);

    void showTimeoutError();
}
