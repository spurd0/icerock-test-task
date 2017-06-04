package com.icerockdev.babenko.managers.impl;

import android.widget.ImageView;

import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.managers.interfaces.FullScreenImageManager;
import com.icerockdev.babenko.managers.interfaces.PicassoManager;

import javax.inject.Inject;

/**
 * Created by Roman Babenko on 14/05/17.
 */

public class FullScreenImageManagerImpl implements FullScreenImageManager {
    @Inject
    PicassoManager mPicasso;

    public FullScreenImageManagerImpl() {
        IceRockApplication.getAppComponent().inject(this);
    }

    public void requestImage(ImageView targetIv, String imageUrl,
                             com.squareup.picasso.Callback imageCallback) {
        mPicasso.getManager().load(imageUrl).into(targetIv, imageCallback);
    }

}
