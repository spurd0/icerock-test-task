package com.icerockdev.babenko.core.dagger.modules;

import android.content.Context;

import com.icerockdev.babenko.providers.impl.PicassoProviderImpl;
import com.icerockdev.babenko.providers.PicassoProvider;

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
    PicassoProvider providePicassoManager() {
        return new PicassoProviderImpl(mContext);
    }
}
