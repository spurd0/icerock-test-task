package com.icerockdev.babenko.core.dagger.modules;

import com.icerockdev.babenko.core.NetworkApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by Roman Babenko on 29/07/17.
 */
@Module(includes = {RetrofitModule.class})
public class ApiModule {

    @Provides
    @Singleton
    NetworkApi provideDataFieldsApi(Retrofit retrofit) {
        return retrofit.create(NetworkApi.class);
    }
}
