package com.icerockdev.babenko.applicaiton

import android.app.Application
import com.icerockdev.babenko.BuildConfig
import com.icerockdev.babenko.applicaiton.dagger.AppComponent
import com.icerockdev.babenko.applicaiton.dagger.DaggerAppComponent
import com.icerockdev.babenko.applicaiton.dagger.modules.ContextModule
import com.icerockdev.babenko.applicaiton.dagger.modules.PicassoModule
import com.squareup.leakcanary.LeakCanary
import timber.log.Timber

/**
 * Created by Roman Babenko on 30/04/17.
 */

class IceRockApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        appComponent = initComponent()
        initLeakCanary()
    }

    private fun initLeakCanary() {
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }

    private fun initComponent(): AppComponent {
        return DaggerAppComponent.builder()
            .contextModule(ContextModule(applicationContext))
            .picassoModule(PicassoModule(applicationContext))
            .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}
