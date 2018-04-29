package com.icerockdev.babenko.managers;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public interface SharedPreferencesManager {
    int getErrorCode();

    void saveErrorCode(int code);

    boolean isUserLogged();

    void setUserLogged(boolean logged);
}
