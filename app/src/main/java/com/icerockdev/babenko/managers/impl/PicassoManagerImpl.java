package com.icerockdev.babenko.managers.impl;

import android.content.Context;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.managers.PicassoManager;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

/**
 * Created by Roman Babenko on 04/06/17.
 */

public class PicassoManagerImpl implements PicassoManager {
    private Picasso mPicasso;

    public PicassoManagerImpl(Context context) {
        mPicasso = new Picasso.Builder(context).listener((picasso, uri, exception) -> {
            if (BuildConfig.DEBUG) {
                exception.printStackTrace();
            }
        }).downloader(new OkHttp3Downloader(context)).build();
    }


    @Override
    public Picasso getManager() {
        return mPicasso;
    }
}
