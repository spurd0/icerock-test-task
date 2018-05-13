package com.icerockdev.babenko.data_source.impl

import com.icerockdev.babenko.core.NetworkApi
import com.icerockdev.babenko.data_source.RemoteImagesDataSource
import com.icerockdev.babenko.model.entities.ImageItem
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class RemoteImagesDataSourceImpl(private val networkApi: NetworkApi, private val imagesUrl: String) : RemoteImagesDataSource {

    override fun requestImages(): Single<Array<ImageItem>> {
        return networkApi
                .requestImages(imagesUrl)
                .timeout(REQUEST_IMAGES_TIMEOUT_SEC, TimeUnit.SECONDS)
    }

    companion object {
        private val REQUEST_IMAGES_TIMEOUT_SEC: Long = 5
    }
}
