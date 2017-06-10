package com.icerockdev.babenko.core.dagger.modules;

/**
 * Created by Roman Babenko on 04/06/17.
 */

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    private Context mContext;

    public ContextModule(Context context) {
        mContext = context;
    }

    @Singleton
    @Provides
    Context getContext() {
        return mContext;
    }
}
