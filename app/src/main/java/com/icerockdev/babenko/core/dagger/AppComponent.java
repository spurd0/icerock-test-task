package com.icerockdev.babenko.core.dagger;

import com.icerockdev.babenko.core.dagger.modules.ContextModule;
import com.icerockdev.babenko.core.dagger.modules.ImagesModule;
import com.icerockdev.babenko.core.dagger.modules.PicassoModule;
import com.icerockdev.babenko.core.dagger.modules.RetrofitModule;
import com.icerockdev.babenko.core.dagger.modules.SharedPreferencesModule;
import com.icerockdev.babenko.ui.full_screen_image.FullScreenImageModelImpl;
import com.icerockdev.babenko.ui.home.HomeModelImpl;
import com.icerockdev.babenko.ui.images.ImagesModelImpl;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Roman Babenko on 04/06/17.
 */
@Singleton
@Component(modules = {RetrofitModule.class, ContextModule.class, PicassoModule.class,
        ImagesModule.class, SharedPreferencesModule.class})
public interface AppComponent {
    void inject(HomeModelImpl homeManager);

    void inject(ImagesModelImpl imagesManager);

    void inject(FullScreenImageModelImpl fullScreenImageManagerImpl);
}
