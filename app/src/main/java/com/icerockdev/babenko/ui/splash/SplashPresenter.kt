package com.icerockdev.babenko.ui.splash

import com.arellomobile.mvp.InjectViewState
import com.icerockdev.babenko.managers.SharedPreferencesManager
import com.icerockdev.babenko.ui.base.BasePresenter

@InjectViewState
class SplashPresenter(private val manager: SharedPreferencesManager) : BasePresenter<SplashView>() {

    fun animationFinished() {
        if (manager.isUserLogged) {
            viewState.startImagesActivity()
        } else {
            viewState.startLoginActivity()
        }
    }
}
