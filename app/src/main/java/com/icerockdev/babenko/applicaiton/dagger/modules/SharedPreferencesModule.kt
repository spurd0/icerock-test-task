package com.icerockdev.babenko.applicaiton.dagger.modules

import android.content.Context
import com.icerockdev.babenko.managers.SharedPreferencesManager
import com.icerockdev.babenko.managers.impl.SharedPreferencesManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Roman Babenko on 17/10/17.
 */
@Module
class SharedPreferencesModule {
    @Singleton
    @Provides
    internal fun provideSharedPreferencesManager(context: Context): SharedPreferencesManager {
        return SharedPreferencesManagerImpl(context)
    }
}
