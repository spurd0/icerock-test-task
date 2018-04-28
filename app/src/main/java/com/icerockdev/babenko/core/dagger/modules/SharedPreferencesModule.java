package com.icerockdev.babenko.core.dagger.modules;

import android.content.Context;

import com.icerockdev.babenko.managers.impl.SharedPreferencesManagerImpl;
import com.icerockdev.babenko.managers.SharedPreferencesManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Roman Babenko on 17/10/17.
 */
@Module
public class SharedPreferencesModule {
    @Singleton
    @Provides
    SharedPreferencesManager provideSharedPreferencesManager(Context context) {
        return new SharedPreferencesManagerImpl(context);
    }
}
