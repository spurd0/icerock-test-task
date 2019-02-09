package com.icerockdev.babenko.applicaiton.dagger.modules

import com.icerockdev.babenko.model.datasource.rest.NetworkApi
import com.icerockdev.babenko.model.datasource.rest.impl.RemoteFieldsDataSourceImpl
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
        return DataFieldsRepositoryImpl(
            RemoteFieldsDataSourceImpl(
                networkApi
            )
        )
    }
}
