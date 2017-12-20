package com.icerockdev.babenko.providers.impl;

import android.content.Context;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.providers.PicassoProvider;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by Roman Babenko on 04/06/17.
 */

public class PicassoProviderImpl implements PicassoProvider {
    private Picasso mPicasso;

    public PicassoProviderImpl(Context context) {
        mPicasso = new Picasso.Builder(context).listener((picasso, uri, exception) -> {
            if (BuildConfig.DEBUG) {
                exception.printStackTrace();
            }
        }).downloader(new OkHttpDownloader(context)).build();
    }


    @Override
    public Picasso getPicasso() {
        return mPicasso;
    }
}
