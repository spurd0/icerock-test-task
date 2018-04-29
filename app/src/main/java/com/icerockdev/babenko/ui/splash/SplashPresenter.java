package com.icerockdev.babenko.ui.splash;

import com.arellomobile.mvp.InjectViewState;
import com.icerockdev.babenko.managers.SharedPreferencesManager;
import com.icerockdev.babenko.ui.base.BasePresenter;

@InjectViewState
public class SplashPresenter extends BasePresenter<SplashView> {
    private final SharedPreferencesManager manager;

    public SplashPresenter(SharedPreferencesManager manager) {
        this.manager = manager;
    }

    public void animationFinished() {
        if (manager.isUserLogged()) {
            getViewState().startImagesActivity();
        } else {
            getViewState().startLoginActivity();
        }
    }
}
