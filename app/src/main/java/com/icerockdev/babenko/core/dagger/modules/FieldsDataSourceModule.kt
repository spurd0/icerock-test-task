package com.icerockdev.babenko.core.dagger.modules

import com.icerockdev.babenko.core.NetworkApi
import com.icerockdev.babenko.data_source.impl.RemoteFieldsDataSourceImpl
import com.icerockdev.babenko.repo.DataFieldsRepository
import com.icerockdev.babenko.repo.impl.DataFieldsRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [(ApiModule::class)])
class FieldsDataSourceModule {
    @Provides
    @Singleton
    internal fun provideDataFieldsRepository(networkApi: NetworkApi): DataFieldsRepository {
        return DataFieldsRepositoryImpl(RemoteFieldsDataSourceImpl(networkApi))
    }
}
