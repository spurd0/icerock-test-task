package com.icerockdev.babenko.managers.interfaces;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public interface SharedPreferencesManager {
    Integer getErrorCode(String key);

    void saveErrorCode(String key, int code);
}
