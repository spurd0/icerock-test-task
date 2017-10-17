package com.icerockdev.babenko.ui.full_screen_image;

import android.widget.ImageView;

import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.managers.interfaces.PicassoManager;

import javax.inject.Inject;

/**
 * Created by Roman Babenko on 14/05/17.
 */

public class FullScreenImageModelImpl implements FullScreenImageModel {
    @Inject
    PicassoManager mPicasso;

    public FullScreenImageModelImpl() {
        IceRockApplication.getAppComponent().inject(this);
    }

    public void requestImage(ImageView targetIv, String imageUrl,
                             com.squareup.picasso.Callback imageCallback) {
        mPicasso.getManager().load(imageUrl).into(targetIv, imageCallback);
    }

}
