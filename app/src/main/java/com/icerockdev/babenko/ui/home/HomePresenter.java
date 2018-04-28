package com.icerockdev.babenko.ui.home;

import com.arellomobile.mvp.InjectViewState;
import com.icerockdev.babenko.ui.base.BasePresenter;
import com.icerockdev.babenko.utils.RxUtils;

/**
 * Created by Roman Babenko on 06/05/17.
 */
@InjectViewState
public class HomePresenter extends BasePresenter<HomeView> {
    private HomeInteractor mHomeInteractor;

    public HomePresenter(HomeInteractor model) {
        mHomeInteractor = model;
    }

    public void requestDataClicked(String url) {

        getViewState().showProgressDialog();
        mHomeInteractor.requestDataFields(url)
                .compose(RxUtils.applyIoMainThreadSchedulersToSingle())
                .doFinally(() -> getViewState().dismissProgressDialog())
                .subscribe(dataFields -> getViewState().gotDataFields(dataFields),
                        throwable -> {
                            if (throwable instanceof RuntimeException) {
                                getViewState().showUrlError();
                            } else {
                                getViewState().showErrorDialog();
                            }
                        });
    }

}


