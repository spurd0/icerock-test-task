package com.icerockdev.babenko.ui.full_screen_image

import android.widget.ImageView

import com.icerockdev.babenko.applicaiton.IceRockApplication
import com.icerockdev.babenko.managers.PicassoManager

import javax.inject.Inject

/**
 * Created by Roman Babenko on 14/05/17.
 */

class FullScreenImageInteractorImpl : FullScreenImageInteractor {
    @Inject
    lateinit var mPicasso: PicassoManager

    init {
        IceRockApplication.appComponent.inject(this)
    }

    override fun requestImage(
        targetIv: ImageView, imageUrl: String,
        imageCallback: com.squareup.picasso.Callback
    ) {
        mPicasso.manager.load(imageUrl).into(targetIv, imageCallback)
    }

}
