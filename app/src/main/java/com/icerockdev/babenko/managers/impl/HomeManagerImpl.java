package com.icerockdev.babenko.managers.impl;

import android.util.Log;

import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.managers.interfaces.HomeManager;
import com.icerockdev.babenko.services.DataFieldsService;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Roman Babenko on 14/05/17.
 */

public class HomeManagerImpl implements HomeManager {
    public static final int ERROR_CODE_RESPONSE_NULL = 1;
    public static final int ERROR_CODE_RESPONSE_OTHER = 2;
    private static final String TAG = "HomeManagerImpl";

    @Inject
    DataFieldsService mDataFieldsService;

    public HomeManagerImpl() {
        IceRockApplication.getAppComponent().inject(this);
    }

    public void requestDataFields(String url, final DataFieldsCallback callback) {
        mDataFieldsService.requestDataFields(url)
                // Run on a background thread
                .subscribeOn(Schedulers.io())
                // Be notified on the main thread
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(dataFields -> Log.d(TAG, "dataFields length is:" + dataFields.length))
                //.compose()
                .subscribe(callback::successResponse,
                        exception -> {
                            Log.e(TAG, exception.getLocalizedMessage());
                            callback.failedResponse(ERROR_CODE_RESPONSE_OTHER);
                        });

    }

}
