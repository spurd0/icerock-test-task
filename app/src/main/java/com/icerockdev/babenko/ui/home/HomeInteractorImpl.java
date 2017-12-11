package com.icerockdev.babenko.ui.home;

import android.util.Patterns;

import com.icerockdev.babenko.core.NetworkApi;
import com.icerockdev.babenko.model.entities.DataField;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Roman Babenko on 14/05/17.
 */

public class HomeInteractorImpl implements HomeInteractor {
    private static final String TAG = HomeInteractorImpl.class.getName();
    @Inject
    NetworkApi mNetworkApi;

    public HomeInteractorImpl(NetworkApi networkApi) {
        mNetworkApi = networkApi;
    }

    public Single<DataField[]> requestDataFields(String url) {
        return Completable.fromAction(() -> {
            if (!Patterns.WEB_URL.matcher(url).matches()) {
                throw new RuntimeException();
            }
        }).andThen(mNetworkApi.requestDataFields(url));
    }

}
