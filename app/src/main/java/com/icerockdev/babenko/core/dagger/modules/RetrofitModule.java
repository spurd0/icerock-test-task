package com.icerockdev.babenko.core.dagger.modules;

import com.icerockdev.babenko.managers.impl.RetrofitManagerImpl;
import com.icerockdev.babenko.managers.interfaces.RetrofitManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Roman Babenko on 04/06/17.
 */
@Module
public class RetrofitModule {
    @Singleton
    @Provides
    RetrofitManager provideRetrofitManager() {
        return new RetrofitManagerImpl();
    }
}
