package com.icerockdev.babenko.managers.interfaces;

import com.icerockdev.babenko.model.DataField;

import java.util.List;

/**
 * Created by Roman Babenko on 14/05/17.
 */

public interface HomeManager {
    void requestDataFields(String url, final DataFieldsCallback callback);

    interface DataFieldsCallback {
        void failedResponse(int errorCode);

        void successResponse(DataField[] response);
    }
}
