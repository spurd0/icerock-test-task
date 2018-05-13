package com.icerockdev.babenko.data_source

import com.icerockdev.babenko.model.entities.DataField

import io.reactivex.Single

interface RemoteFieldsDataSource {
    fun requestDataFields(url: String): Single<Array<DataField>>
}
