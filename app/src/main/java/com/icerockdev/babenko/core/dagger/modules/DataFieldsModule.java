package com.icerockdev.babenko.core.dagger.modules;

import com.icerockdev.babenko.interfaces.NetworkApi;
import com.icerockdev.babenko.services.DataFieldsService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Roman Babenko on 29/07/17.
 */
@Module(includes = {ApiModule.class})
public class DataFieldsModule {
    @Provides
    @Singleton
    DataFieldsService provideDataFieldsService(NetworkApi networkApi) {
        return new DataFieldsService(networkApi);
    }
}
