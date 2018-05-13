package com.icerockdev.babenko.ui.images

import com.icerockdev.babenko.model.entities.ImageItem

import io.reactivex.Single

/**
 * Created by Roman Babenko on 10/05/17.
 */

interface ImagesInteractor {
    fun requestPicturesList(): Single<List<ImageItem>>
}
