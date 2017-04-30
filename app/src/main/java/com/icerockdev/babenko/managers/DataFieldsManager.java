package com.icerockdev.babenko.managers;

/**
 * Created by Roman Babenko on 30/04/17.
 */

public class DataFieldsManager {
    private static DataFieldsManager sInstance;

    public static DataFieldsManager getInstance() {
        if (sInstance != null)
            return sInstance;
        sInstance = new DataFieldsManager();
        return sInstance;
    }

    public void requestDataFields(String Url, RequestDataFieldsInterface callback){

    }

    public interface RequestDataFieldsInterface {
        void successfulResponse(String data);
        void errorResponse(String error);
    }
}
