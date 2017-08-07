package com.icerockdev.babenko.core.dagger;

import com.icerockdev.babenko.core.dagger.modules.ContextModule;
import com.icerockdev.babenko.core.dagger.modules.DataFieldsModule;
import com.icerockdev.babenko.core.dagger.modules.ImagesModule;
import com.icerockdev.babenko.core.dagger.modules.PicassoModule;
import com.icerockdev.babenko.core.dagger.modules.RetrofitModule;
import com.icerockdev.babenko.models.impl.FullScreenImageModelImpl;
import com.icerockdev.babenko.models.impl.HomeModelImpl;
import com.icerockdev.babenko.models.impl.ImagesModelImpl;
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

    void inject(HomeModelImpl homeManager);

    void inject(ImagesModelImpl imagesManager);

    void inject(FullScreenImageModelImpl fullScreenImageManagerImpl);
}
