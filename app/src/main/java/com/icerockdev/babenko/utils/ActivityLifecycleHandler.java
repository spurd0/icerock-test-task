package com.icerockdev.babenko.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

public class ActivityLifecycleHandler implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = ActivityLifecycleHandler.class.getName();

    @SuppressLint("LongLogTag")
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated " + activity.getClass().getSimpleName());
    }

    @SuppressLint("LongLogTag")
    public void onActivityStarted(Activity activity) {
        Log.d(TAG, "onActivityStarted " + activity.getClass().getSimpleName());
    }

    @SuppressLint("LongLogTag")
    public void onActivityResumed(Activity activity) {
        Log.d(TAG, "onActivityResumed " + activity.getClass().getSimpleName());
    }


    public void onActivityPaused(Activity activity) {
        Log.d(TAG, "onActivityPaused " + activity.getClass().getSimpleName());
    }

    public void onActivityStopped(Activity activity) {
        Log.d(TAG, "onActivityStopped " + activity.getClass().getSimpleName());
    }


    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Log.d(TAG, "onActivitySaveInstanceState " + activity.getClass().getSimpleName());
    }

    public void onActivityDestroyed(Activity activity) {
        Log.d(TAG, "onActivityDestroyed " + activity.getClass().getSimpleName());
    }
}