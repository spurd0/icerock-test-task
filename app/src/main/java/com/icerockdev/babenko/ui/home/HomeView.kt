package com.icerockdev.babenko.ui.home

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.icerockdev.babenko.model.entities.DataField
import com.icerockdev.babenko.ui.base.views.ProgressBaseView

/**
 * Created by Roman Babenko on 06/05/17.
 */
@StateStrategyType(SkipStrategy::class)
interface HomeView : ProgressBaseView {
    fun showErrorDialog()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showUrlError()

    fun gotDataFields(fields: Array<DataField>)

    fun showTimeoutError()
}
