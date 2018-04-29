package com.icerockdev.babenko.repo;

import com.icerockdev.babenko.model.entities.DataField;

import io.reactivex.Single;

public interface DataFieldsRepository {
    Single<DataField[]> requestDataFields(String url);
}
