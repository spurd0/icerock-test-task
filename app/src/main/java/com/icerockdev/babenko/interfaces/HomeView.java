package com.icerockdev.babenko.interfaces;

/**
 * Created by Roman Babenko on 06/05/17.
 */

public interface HomeView {
    String getUrl();

    void showProgressDialog();

    void dismissProgressDialog();

    void showErrorMessage(String message);

    void showUrlError(String error);

}
