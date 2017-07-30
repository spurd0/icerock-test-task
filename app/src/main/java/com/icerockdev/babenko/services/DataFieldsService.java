package com.icerockdev.babenko.services;

import com.icerockdev.babenko.interfaces.NetworkApi;
import com.icerockdev.babenko.model.DataField;

import io.reactivex.Observable;

/**
 * Created by Roman Babenko on 29/07/17.
 */

public class DataFieldsService {
    private NetworkApi mDataFieldsApi;

    public DataFieldsService(NetworkApi networkApi) {
        mDataFieldsApi = networkApi;
    }

    public Observable<DataField[]> requestDataFields(String url) {
        return mDataFieldsApi.requestDataFields(url);
    }
}
