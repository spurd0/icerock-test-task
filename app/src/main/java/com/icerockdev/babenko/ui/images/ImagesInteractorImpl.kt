package com.icerockdev.babenko.ui.images

import com.icerockdev.babenko.model.entities.ImageItem
import com.icerockdev.babenko.repo.ImageRepository
import io.reactivex.Single

/**
 * Created by Roman Babenko on 10/05/17.
 */

class ImagesInteractorImpl(private val mImageRepository: ImageRepository) : ImagesInteractor {

    override fun requestPicturesList(): Single<List<ImageItem>> {
        return mImageRepository
                .requestImages()
                .map { array -> array.toMutableList() }
    }

    companion object {
        private val TAG = "ImagesInteractorImpl"
    }

}
