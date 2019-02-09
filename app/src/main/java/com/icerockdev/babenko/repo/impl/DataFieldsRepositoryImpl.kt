package com.icerockdev.babenko.repo.impl

import com.icerockdev.babenko.model.datasource.rest.RemoteFieldsDataSource
import com.icerockdev.babenko.model.entities.DataField
import com.icerockdev.babenko.repo.DataFieldsRepository

import io.reactivex.Single

class DataFieldsRepositoryImpl(private val dataSource: RemoteFieldsDataSource) : DataFieldsRepository {

    override fun requestDataFields(url: String): Single<Array<DataField>> {
        return dataSource.requestDataFields(url)
    }
}
