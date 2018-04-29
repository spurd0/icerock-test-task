package com.icerockdev.babenko.ui.splash;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.icerockdev.babenko.ui.base.views.BaseView;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface SplashView extends BaseView {
    @StateStrategyType(SkipStrategy.class)
    void startImagesActivity();

    @StateStrategyType(SkipStrategy.class)
    void startLoginActivity();
}
