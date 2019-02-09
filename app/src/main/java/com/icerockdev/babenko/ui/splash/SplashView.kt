package com.icerockdev.babenko.ui.splash

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.icerockdev.babenko.ui.base.views.BaseView

@StateStrategyType(SkipStrategy::class)
interface SplashView : BaseView {
    fun startImagesActivity()

    fun startLoginActivity()
}
