package com.icerockdev.babenko.core.dagger.modules

import com.icerockdev.babenko.core.NetworkApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by Roman Babenko on 29/07/17.
 */
@Module(includes = [(RetrofitModule::class)])
class ApiModule {

    @Provides
    @Singleton
    internal fun provideDataFieldsApi(retrofit: Retrofit): NetworkApi {
        return retrofit.create(NetworkApi::class.java!!)
    }
}
