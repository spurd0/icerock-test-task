package com.icerockdev.babenko;

import android.app.Application;

import com.icerockdev.babenko.core.dagger.AppComponent;
import com.icerockdev.babenko.core.dagger.DaggerAppComponent;
import com.icerockdev.babenko.core.dagger.modules.ContextModule;
import com.icerockdev.babenko.core.dagger.modules.PicassoModule;
import com.icerockdev.babenko.utils.ActivityLifecycleHandler;

/**
 * Created by Roman Babenko on 30/04/17.
 */

public class IceRockApplication extends Application {
    private static AppComponent sAppComponent;

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sAppComponent = initComponent();
        initActivityWatcher();
    }

    private void initActivityWatcher() {
        ActivityLifecycleHandler activityLifecycleHandler = new ActivityLifecycleHandler();
        registerActivityLifecycleCallbacks(activityLifecycleHandler);
    }

    private AppComponent initComponent() {
        return DaggerAppComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()))
                .picassoModule(new PicassoModule(getApplicationContext()))
                .build();
    }
}
