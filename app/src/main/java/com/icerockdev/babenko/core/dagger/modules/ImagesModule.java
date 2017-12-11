package com.icerockdev.babenko.core.dagger.modules;

import com.icerockdev.babenko.core.NetworkApi;
import com.icerockdev.babenko.repo.impl.ImageRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Roman Babenko on 29/07/17.
 */
@Module(includes = {ApiModule.class})
public class ImagesModule {

    @Provides
    @Singleton
    ImageRepositoryImpl provideImageService(NetworkApi networkApi) {
        return new ImageRepositoryImpl(networkApi);
    }
}
