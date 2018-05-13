package com.icerockdev.babenko.core.dagger.modules

/**
 * Created by Roman Babenko on 04/06/17.
 */

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule(@get:Singleton
                    @get:Provides
                    internal val context: Context)
