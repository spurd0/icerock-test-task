package com.icerockdev.babenko.ui.images

import com.icerockdev.babenko.model.entities.ImageItem
import com.icerockdev.babenko.ui.base.views.ProgressBaseView

/**
 * Created by Roman Babenko on 10/05/17.
 */

interface ImagesView : ProgressBaseView {
    fun showErrorDialog()

    fun showListIsEmptyError()

    fun showImagesList(imageItems: List<ImageItem>)
}
