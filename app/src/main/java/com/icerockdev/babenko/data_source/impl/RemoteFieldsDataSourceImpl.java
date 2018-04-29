package com.icerockdev.babenko.data_source.impl;

import com.icerockdev.babenko.core.NetworkApi;
import com.icerockdev.babenko.data_source.RemoteFieldsDataSource;
import com.icerockdev.babenko.model.entities.DataField;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;

public class RemoteFieldsDataSourceImpl implements RemoteFieldsDataSource {
    private static final long REQUEST_FIELDS_TIMEOUT_SEC = 5;
    private final NetworkApi networkApi;

    public RemoteFieldsDataSourceImpl(NetworkApi networkApi) {
        this.networkApi = networkApi;
    }

    @Override
    public Single<DataField[]> requestDataFields(String url) {
        return networkApi
                .requestDataFields(url)
                .timeout(REQUEST_FIELDS_TIMEOUT_SEC, TimeUnit.SECONDS);
    }
}
