package com.icerockdev.babenko.ui.base.views;

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

/**
 * Created by Roman Babenko on 14/05/17.
 */
@StateStrategyType(AddToEndSingleStrategy.class)
public interface ProgressBaseView extends BaseView {
    @StateStrategyType(SkipStrategy.class)
    void showProgressDialog();

    @StateStrategyType(SkipStrategy.class)
    void dismissProgressDialog();
}
