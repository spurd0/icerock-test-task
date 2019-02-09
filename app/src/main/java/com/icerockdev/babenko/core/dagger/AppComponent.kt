package com.icerockdev.babenko.core.dagger

import com.icerockdev.babenko.core.dagger.modules.*
import com.icerockdev.babenko.ui.full_screen_image.FullScreenImageInteractorImpl
import com.icerockdev.babenko.ui.home.HomeActivity
import com.icerockdev.babenko.ui.images.ImagesActivity
import com.icerockdev.babenko.ui.splash.SplashActivity
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Roman Babenko on 04/06/17.
 */
@Singleton
@Component(
    modules = arrayOf(
        RetrofitModule::class, ContextModule::class, PicassoModule::class,
        ImagesDataSourceModule::class, SharedPreferencesModule::class,
        FieldsDataSourceModule::class
    )
)
interface AppComponent {
    fun inject(fullScreenImageManagerImpl: FullScreenImageInteractorImpl)

    fun inject(homeActivity: HomeActivity)

    fun inject(imagesActivity: ImagesActivity)

    fun inject(splashActivity: SplashActivity)
}
