package com.icerockdev.babenko.core.dagger.modules;

import android.content.Context;

import com.icerockdev.babenko.managers.impl.PicassoManagerImpl;
import com.icerockdev.babenko.managers.interfaces.PicassoManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Roman Babenko on 04/06/17.
 */
@Module
public class PicassoModule {
    Context mContext;

    public PicassoModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    PicassoManager providePicassoManager() {
        return new PicassoManagerImpl(mContext);
    }
}
