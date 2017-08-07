package com.icerockdev.babenko.models.interfaces;

import com.icerockdev.babenko.model.DataField;

/**
 * Created by Roman Babenko on 14/05/17.
 */

public interface HomeModel {
    void requestDataFields(String url, final DataFieldsCallback callback);

    interface DataFieldsCallback {
        void failedResponse(int errorCode);

        void successResponse(DataField[] response);
    }
}
