package com.icerockdev.babenko.core.dagger.modules

import android.content.Context
import com.icerockdev.babenko.managers.PicassoManager
import com.icerockdev.babenko.managers.impl.PicassoManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Roman Babenko on 04/06/17.
 */
@Module
class PicassoModule(private val mContext: Context) {

    @Provides
    @Singleton
    internal fun providePicassoManager(): PicassoManager {
        return PicassoManagerImpl(mContext)
    }
}
