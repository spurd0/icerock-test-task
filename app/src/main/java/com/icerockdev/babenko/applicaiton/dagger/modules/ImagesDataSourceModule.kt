package com.icerockdev.babenko.applicaiton.dagger.modules

import android.content.Context
import com.icerockdev.babenko.R
import com.icerockdev.babenko.model.datasource.rest.NetworkApi
import com.icerockdev.babenko.model.datasource.rest.impl.RemoteImagesDataSourceImpl
import com.icerockdev.babenko.repo.ImageRepository
import com.icerockdev.babenko.repo.impl.ImageRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Roman Babenko on 29/07/17.
 */
@Module(includes = [(ApiModule::class)])
class ImagesDataSourceModule {

    @Provides
    @Singleton
    internal fun provideImageRepository(networkApi: NetworkApi, context: Context): ImageRepository {
        return ImageRepositoryImpl(
            RemoteImagesDataSourceImpl(
                networkApi,
                context.getString(R.string.request_images_url)
            )
        )
    }


}
