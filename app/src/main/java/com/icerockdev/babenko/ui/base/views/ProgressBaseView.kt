package com.icerockdev.babenko.ui.base.views

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * Created by Roman Babenko on 14/05/17.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface ProgressBaseView : BaseView {
    @StateStrategyType(SkipStrategy::class)
    fun showProgressDialog()

    @StateStrategyType(SkipStrategy::class)
    fun dismissProgressDialog()
}
