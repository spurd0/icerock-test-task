package com.icerockdev.babenko.core.dagger.modules;

import android.content.Context;

import com.icerockdev.babenko.managers.PicassoManager;
import com.icerockdev.babenko.managers.impl.PicassoManagerImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Roman Babenko on 04/06/17.
 */
@Module
public class PicassoModule {
    private Context mContext;

    public PicassoModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    PicassoManager providePicassoManager() {
        return new PicassoManagerImpl(mContext);
    }
}
