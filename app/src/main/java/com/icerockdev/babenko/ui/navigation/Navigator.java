package com.icerockdev.babenko.ui.navigation;

import android.content.Context;

import com.icerockdev.babenko.model.entities.DataField;
import com.icerockdev.babenko.ui.data_fields.DataFieldsActivity;
import com.icerockdev.babenko.ui.full_screen_image.FullScreenImageActivity;
import com.icerockdev.babenko.ui.home.HomeActivity;
import com.icerockdev.babenko.ui.images.ImagesActivity;

public class Navigator {

    public void navigateToImagesActivity(Context context) {
        context.startActivity(ImagesActivity.getLaunchingIntent(context));
    }


    public void navigateToLoginActivity(Context context) {
        context.startActivity(HomeActivity.getLaunchingIntent(context));
    }

    public void navigateToFullScreenImageActivity(Context context, String imageUrl) {
        context.startActivity(FullScreenImageActivity.getStartingIntent(context, imageUrl));
    }

    public void navigateToDataFieldsActivity(Context context, DataField[] data) {
        context.startActivity(DataFieldsActivity.getStartingIntent(context, data));
    }
}
