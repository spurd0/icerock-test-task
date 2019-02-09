package com.icerockdev.babenko.model.datasource.rest

import com.icerockdev.babenko.model.entities.ImageItem

import io.reactivex.Single

interface RemoteImagesDataSource {
    fun requestImages(): Single<Array<ImageItem>>
}
