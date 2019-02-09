package com.icerockdev.babenko.ui.full_screen_image

import android.widget.ImageView

/**
 * Created by Roman Babenko on 14/05/17.
 */

interface FullScreenImageInteractor {

    fun requestImage(
        targetIv: ImageView, imageUrl: String,
        imageCallback: com.squareup.picasso.Callback
    )

}
