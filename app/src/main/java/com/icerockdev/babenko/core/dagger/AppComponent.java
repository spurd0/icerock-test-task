package com.icerockdev.babenko.core.dagger;

import com.icerockdev.babenko.core.dagger.modules.ContextModule;
import com.icerockdev.babenko.core.dagger.modules.ImagesModule;
import com.icerockdev.babenko.core.dagger.modules.PicassoModule;
import com.icerockdev.babenko.core.dagger.modules.RetrofitModule;
import com.icerockdev.babenko.core.dagger.modules.SharedPreferencesModule;
import com.icerockdev.babenko.ui.full_screen_image.FullScreenImageInteractorImpl;
import com.icerockdev.babenko.ui.home.HomeActivity;
import com.icerockdev.babenko.ui.home.HomeInteractorImpl;
import com.icerockdev.babenko.ui.images.ImagesActivity;
import com.icerockdev.babenko.ui.images.ImagesInteractorImpl;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Roman Babenko on 04/06/17.
 */
@Singleton
@Component(modules = {RetrofitModule.class, ContextModule.class, PicassoModule.class,
        ImagesModule.class, SharedPreferencesModule.class})
public interface AppComponent {
    void inject(FullScreenImageInteractorImpl fullScreenImageManagerImpl);

    void inject(HomeActivity homeActivity);

    void inject(ImagesActivity imagesActivity);
}
