package com.icerockdev.babenko.ui.navigation;

import android.content.Context;

import com.icerockdev.babenko.ui.home.HomeActivity;
import com.icerockdev.babenko.ui.images.ImagesActivity;

public class Navigator {

    public void navigateToImagesActivity(Context context) {
        context.startActivity(ImagesActivity.getLaunchingIntent(context));
    }


    public void navigateToLoginActivity(Context context) {
        context.startActivity(HomeActivity.getLaunchingIntent(context));
    }
}
