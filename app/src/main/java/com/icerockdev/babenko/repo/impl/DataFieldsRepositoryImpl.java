package com.icerockdev.babenko.repo.impl;

import com.icerockdev.babenko.data_source.RemoteFieldsDataSource;
import com.icerockdev.babenko.model.entities.DataField;
import com.icerockdev.babenko.repo.DataFieldsRepository;

import io.reactivex.Single;

public class DataFieldsRepositoryImpl implements DataFieldsRepository {
    private final RemoteFieldsDataSource dataSource;

    public DataFieldsRepositoryImpl(RemoteFieldsDataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Single<DataField[]> requestDataFields(String url) {
        return dataSource.requestDataFields(url);
    }
}
