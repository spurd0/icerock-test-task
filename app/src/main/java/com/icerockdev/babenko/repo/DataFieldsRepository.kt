package com.icerockdev.babenko.repo

import com.icerockdev.babenko.model.entities.DataField

import io.reactivex.Single

interface DataFieldsRepository {
    fun requestDataFields(url: String): Single<Array<DataField>>
}
