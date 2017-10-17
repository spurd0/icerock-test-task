package com.icerockdev.babenko.ui.home;

import android.util.Log;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.interfaces.NetworkApi;
import com.icerockdev.babenko.model.entities.DataField;
import com.icerockdev.babenko.utils.RxUtils;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.subjects.AsyncSubject;

/**
 * Created by Roman Babenko on 14/05/17.
 */

public class HomeModelImpl implements HomeModel {
    private static final String TAG = HomeModelImpl.class.getName();
    @Inject
    NetworkApi mNetworkApi;
    private AsyncSubject<DataField[]> mDataFieldsSubject = AsyncSubject.create();

    public HomeModelImpl() {
        IceRockApplication.getAppComponent().inject(this);
    }

    public Single<DataField[]> requestDataFields(String url) {
        return mNetworkApi.requestDataFields(url)
                .compose(RxUtils.applyIoMainThreadSchedulersToSingle())
                .doOnSuccess(dataFields -> {
                    if (BuildConfig.DEBUG)
                        Log.d(TAG, "dataFields length is:" + dataFields.length);
                    mDataFieldsSubject.onNext(dataFields);
                    mDataFieldsSubject.onComplete();
                })
                .doOnError(throwable -> {
                    mDataFieldsSubject.onError(throwable);
                });
    }

    public Single<DataField[]> getDataFieldsResult() {
        return mDataFieldsSubject.singleOrError();
    }
}
