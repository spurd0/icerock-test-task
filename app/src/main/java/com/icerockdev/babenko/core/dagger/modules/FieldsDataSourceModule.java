package com.icerockdev.babenko.core.dagger.modules;

import com.icerockdev.babenko.core.NetworkApi;
import com.icerockdev.babenko.data_source.impl.RemoteFieldsDataSourceImpl;
import com.icerockdev.babenko.repo.DataFieldsRepository;
import com.icerockdev.babenko.repo.impl.DataFieldsRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ApiModule.class})
public class FieldsDataSourceModule {
    @Provides
    @Singleton
    DataFieldsRepository provideDataFieldsRepository(NetworkApi networkApi) {
        return new DataFieldsRepositoryImpl(new RemoteFieldsDataSourceImpl(networkApi));
    }
}
