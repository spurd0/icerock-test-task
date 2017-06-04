package com.icerockdev.babenko.managers.impl;

import android.content.Context;
import android.net.Uri;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.managers.interfaces.PicassoManager;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by Roman Babenko on 04/06/17.
 */

public class PicassoManagerImpl implements PicassoManager {
    private Picasso mPicasso;

    public PicassoManagerImpl(Context context) {
        mPicasso = new Picasso.Builder(context).listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                if (BuildConfig.DEBUG) exception.printStackTrace();
            }
        }).downloader(new OkHttpDownloader(context)).build();
    }


    @Override
    public Picasso getManager() {
        return mPicasso;
    }
}
