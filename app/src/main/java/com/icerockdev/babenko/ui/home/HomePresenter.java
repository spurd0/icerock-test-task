package com.icerockdev.babenko.ui.home;

import android.util.Patterns;

import com.arellomobile.mvp.InjectViewState;
import com.icerockdev.babenko.ui.BasePresenter;
import com.icerockdev.babenko.utils.RxUtils;

/**
 * Created by Roman Babenko on 06/05/17.
 */
@InjectViewState
public class HomePresenter extends BasePresenter<HomeView> {
    private static final int REQUEST_RUNNING = 1;
    private static final int REQUEST_COMPLETED = 2;
    private static final int VIEW_DETACHED = 3;
    private HomeModel mModel;
    private int mDataFieldsRequestState = -1;

    public HomePresenter(HomeModel model) {
        mModel = model;
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        checkRequestState();
    }

    public void requestDataClicked(String url) {
        if (!Patterns.WEB_URL.matcher(url).matches()) {
            if (getViewState() != null)
                getViewState().showUrlError();
            return;
        }
        if (getViewState() != null)
            getViewState().showProgressDialog();
        mDataFieldsRequestState = REQUEST_RUNNING;
        mModel.requestDataFields(url)
                .compose(RxUtils.applyIoMainThreadSchedulersToSingle())
                .subscribe((dataFields, throwable) -> {
                    mDataFieldsRequestState = REQUEST_COMPLETED;
                    getViewState().dismissProgressDialog();
                    if (throwable != null) {
                        getViewState().showErrorDialog(throwable.getMessage());
                        return;
                    }
                    getViewState().gotDataFields(dataFields);
                });
    }

    @Override
    public void detachView(HomeView view) {
        mDataFieldsRequestState = mDataFieldsRequestState == REQUEST_RUNNING ? VIEW_DETACHED
                : mDataFieldsRequestState;
        super.detachView(view);
    }

    private void checkRequestState() {
        if (mDataFieldsRequestState != VIEW_DETACHED)
            return;
        mModel.getDataFieldsResult()
                .compose(RxUtils.applyIoMainThreadSchedulersToSingle())
                .subscribe((dataFields, throwable) -> {
                    mDataFieldsRequestState = 0;
                    if (throwable != null) {
                        getViewState().showErrorDialog(throwable.getMessage());
                        return;
                    }
                    getViewState().gotDataFields(dataFields);
                });
    }
}


