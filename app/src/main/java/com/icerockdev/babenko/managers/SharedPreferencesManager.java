package com.icerockdev.babenko.managers;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public interface SharedPreferencesManager {
    Integer getErrorCode(String key);

    void saveErrorCode(String key, int code);
}
