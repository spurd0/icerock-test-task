package com.icerockdev.babenko.ui.navigation

import android.content.Context

import com.icerockdev.babenko.model.entities.DataField
import com.icerockdev.babenko.ui.data_fields.DataFieldsActivity
import com.icerockdev.babenko.ui.full_screen_image.FullScreenImageActivity
import com.icerockdev.babenko.ui.home.HomeActivity
import com.icerockdev.babenko.ui.images.ImagesActivity

class Navigator {
    fun navigateToImagesActivity(context: Context) {
        context.startActivity(ImagesActivity.getLaunchingIntent(context))
    }

    fun navigateToLoginActivity(context: Context) {
        context.startActivity(HomeActivity.getLaunchingIntent(context))
    }

    fun navigateToFullScreenImageActivity(context: Context, imageUrl: String) {
        context.startActivity(FullScreenImageActivity.getStartingIntent(context, imageUrl))
    }

    fun navigateToDataFieldsActivity(context: Context, data: Array<DataField>) {
        context.startActivity(DataFieldsActivity.getStartingIntent(context, data))
    }
}
