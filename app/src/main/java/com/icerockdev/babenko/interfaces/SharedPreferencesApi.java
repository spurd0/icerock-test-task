package com.icerockdev.babenko.interfaces;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public interface SharedPreferencesApi {
    String getErrorMessage();

    void saveErrorMessage(String message);
}
