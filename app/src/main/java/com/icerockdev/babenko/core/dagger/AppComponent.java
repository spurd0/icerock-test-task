package com.icerockdev.babenko.core.dagger;

import com.icerockdev.babenko.core.dagger.modules.ApiModule;
import com.icerockdev.babenko.core.dagger.modules.ContextModule;
import com.icerockdev.babenko.core.dagger.modules.DataFieldsModule;
import com.icerockdev.babenko.core.dagger.modules.ImagesModule;
import com.icerockdev.babenko.core.dagger.modules.PicassoModule;
import com.icerockdev.babenko.core.dagger.modules.RetrofitModule;
import com.icerockdev.babenko.managers.impl.FullScreenImageManagerImpl;
import com.icerockdev.babenko.managers.impl.HomeManagerImpl;
import com.icerockdev.babenko.managers.impl.ImagesManagerImpl;
import com.icerockdev.babenko.managers.impl.SharedPreferencesManagerImpl;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Roman Babenko on 04/06/17.
 */
@Singleton
@Component(modules = {RetrofitModule.class, ContextModule.class, PicassoModule.class,
        DataFieldsModule.class, ImagesModule.class})
public interface AppComponent {
    void inject(SharedPreferencesManagerImpl preferencesManager);

    void inject(HomeManagerImpl homeManager);

    void inject(ImagesManagerImpl imagesManager);

    void inject(FullScreenImageManagerImpl fullScreenImageManagerImpl);
}
