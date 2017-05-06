package com.icerockdev.babenko.presenters;

/**
 * Created by Roman Babenko on 06/05/17.
 */

abstract class BasePresenter<View> {
    private View mView;

    public View getView() {
        return mView;
    }

    public void attachView(View view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
    }
}
