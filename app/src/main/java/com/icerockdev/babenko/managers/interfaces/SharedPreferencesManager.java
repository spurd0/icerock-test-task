package com.icerockdev.babenko.managers.interfaces;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public interface SharedPreferencesManager {
    Integer getErrorCode();

    void saveErrorCode(int code);
}
