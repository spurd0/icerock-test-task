package com.icerockdev.babenko.managers.interfaces;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public interface SharedPreferencesManager {
    String getErrorMessage();

    void saveErrorMessage(String message);
}
