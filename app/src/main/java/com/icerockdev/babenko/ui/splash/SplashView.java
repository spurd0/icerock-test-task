package com.icerockdev.babenko.ui.splash;

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.icerockdev.babenko.ui.base.views.BaseView;

@StateStrategyType(SkipStrategy.class)
public interface SplashView extends BaseView {
    void startImagesActivity();

    void startLoginActivity();
}
