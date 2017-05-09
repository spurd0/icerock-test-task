package com.icerockdev.babenko.interfaces;

import com.icerockdev.babenko.model.DataFieldResponse;

/**
 * Created by Roman Babenko on 06/05/17.
 */

public interface HomeView extends BaseView {
    String getUrlFromForm();

    void showProgressDialog();

    void dismissProgressDialog();

    void showErrorDialog(String message);

    void showUrlError(String error);

    void gotDataFields(DataFieldResponse[] fields);

}
