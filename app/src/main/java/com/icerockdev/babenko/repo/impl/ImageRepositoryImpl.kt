package com.icerockdev.babenko.repo.impl

import com.icerockdev.babenko.model.datasource.rest.RemoteImagesDataSource
import com.icerockdev.babenko.model.entities.ImageItem
import com.icerockdev.babenko.repo.ImageRepository

import io.reactivex.Single


/**
 * Created by Roman Babenko on 29/07/17.
 */

class ImageRepositoryImpl(private val remoteDataSource: RemoteImagesDataSource) : ImageRepository {

    override fun requestImages(): Single<Array<ImageItem>> {
        return remoteDataSource.requestImages()
    }
}
