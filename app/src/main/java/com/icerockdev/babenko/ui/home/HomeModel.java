package com.icerockdev.babenko.ui.home;

import com.icerockdev.babenko.model.entities.DataField;

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
