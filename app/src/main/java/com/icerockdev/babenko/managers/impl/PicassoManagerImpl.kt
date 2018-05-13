package com.icerockdev.babenko.managers.impl

import android.content.Context

import com.icerockdev.babenko.BuildConfig
import com.icerockdev.babenko.managers.PicassoManager
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

/**
 * Created by Roman Babenko on 04/06/17.
 */

class PicassoManagerImpl(context: Context) : PicassoManager {
    override val manager: Picasso

    init {
        manager = Picasso.Builder(context).listener { picasso, uri, exception ->
            if (BuildConfig.DEBUG) {
                exception.printStackTrace()
            }
        }.downloader(OkHttp3Downloader(context)).build()
    }
}
