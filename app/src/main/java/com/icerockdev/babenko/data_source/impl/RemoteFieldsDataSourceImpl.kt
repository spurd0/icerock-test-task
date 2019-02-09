package com.icerockdev.babenko.data_source.impl

import com.icerockdev.babenko.core.NetworkApi
import com.icerockdev.babenko.data_source.RemoteFieldsDataSource
import com.icerockdev.babenko.model.entities.DataField
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class RemoteFieldsDataSourceImpl(private val networkApi: NetworkApi) : RemoteFieldsDataSource {

    override fun requestDataFields(url: String): Single<Array<DataField>> {
        return networkApi
            .requestDataFields(url)
            .timeout(REQUEST_FIELDS_TIMEOUT_SEC, TimeUnit.SECONDS)
    }

    companion object {
        private val REQUEST_FIELDS_TIMEOUT_SEC: Long = 5
    }
}
