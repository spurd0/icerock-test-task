package com.icerockdev.babenko.ui.home;

import com.icerockdev.babenko.model.entities.DataField;

import io.reactivex.Single;

/**
 * Created by Roman Babenko on 14/05/17.
 */

public interface HomeModel {
    Single<DataField[]> requestDataFields(String url);

    Single<DataField[]> getDataFieldsResult();
}
