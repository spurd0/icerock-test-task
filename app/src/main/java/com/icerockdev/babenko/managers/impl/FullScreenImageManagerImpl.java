package com.icerockdev.babenko.managers.impl;

import android.net.Uri;
import android.widget.ImageView;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.managers.interfaces.FullScreenImageManager;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by Roman Babenko on 14/05/17.
 */

public class FullScreenImageManagerImpl implements FullScreenImageManager {

    public void requestImage(ImageView targetIv, String imageUrl,
                             com.squareup.picasso.Callback imageCallback) {
        Picasso picasso = new Picasso.Builder(IceRockApplication.getInstance()).listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                if (BuildConfig.DEBUG) exception.printStackTrace();
            }
        }).downloader(new OkHttpDownloader(IceRockApplication.getInstance())).build();
        picasso.load(imageUrl).into(targetIv, imageCallback);
    }

}
