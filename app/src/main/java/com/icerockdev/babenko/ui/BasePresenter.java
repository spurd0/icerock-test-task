package com.icerockdev.babenko.ui;

/**
 * Created by Roman Babenko on 06/05/17.
 */

public abstract class BasePresenter<View> {
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
