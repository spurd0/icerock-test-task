package com.icerockdev.babenko.data_source

import com.icerockdev.babenko.model.entities.ImageItem

import io.reactivex.Single

interface RemoteImagesDataSource {
    fun requestImages(): Single<Array<ImageItem>>
}
