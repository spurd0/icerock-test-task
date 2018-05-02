package com.icerockdev.babenko.repo

import com.icerockdev.babenko.model.entities.ImageItem

import io.reactivex.Single

/**
 * Created by spurdo on 11/12/2017.
 */

interface ImageRepository {
    fun requestImages(): Single<Array<ImageItem>>
}
