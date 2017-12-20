package com.icerockdev.babenko.ui.full_screen_image;

import android.widget.ImageView;

import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.providers.PicassoProvider;

import javax.inject.Inject;

/**
 * Created by Roman Babenko on 14/05/17.
 */

public class FullScreenImageInteractorImpl implements FullScreenImageInteractor {
    @Inject
    PicassoProvider mPicasso;

    public FullScreenImageInteractorImpl() {
        IceRockApplication.getAppComponent().inject(this);
    }

    public void requestImage(ImageView targetIv, String imageUrl,
                             com.squareup.picasso.Callback imageCallback) {
        mPicasso.getPicasso().load(imageUrl).into(targetIv, imageCallback);
    }

}
