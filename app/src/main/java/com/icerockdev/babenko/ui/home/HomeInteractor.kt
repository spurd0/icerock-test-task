package com.icerockdev.babenko.ui.home

import com.icerockdev.babenko.model.entities.DataField

import io.reactivex.Single

/**
 * Created by Roman Babenko on 14/05/17.
 */

interface HomeInteractor {

    fun requestDataFields(url: String): Single<Array<DataField>>

}
