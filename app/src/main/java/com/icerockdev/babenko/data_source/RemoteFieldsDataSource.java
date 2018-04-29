package com.icerockdev.babenko.data_source;

import com.icerockdev.babenko.model.entities.DataField;

import io.reactivex.Single;

public interface RemoteFieldsDataSource {
    Single<DataField[]> requestDataFields(String url);
}
