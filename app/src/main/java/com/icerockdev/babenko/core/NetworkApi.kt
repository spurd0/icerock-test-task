package com.icerockdev.babenko.core

import com.icerockdev.babenko.model.entities.DataField
import com.icerockdev.babenko.model.entities.ImageItem

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Created by Roman Babenko on 30/04/17.
 */

interface NetworkApi {
    @GET
    fun requestDataFields(@Url url: String): Single<Array<DataField>>

    @GET
    fun requestImages(@Url url: String): Single<Array<ImageItem>>
}
