package com.icerockdev.babenko.ui.home

import com.arellomobile.mvp.InjectViewState
import com.icerockdev.babenko.model.entities.DataField
import com.icerockdev.babenko.model.errors.IncorrectEmailException
import com.icerockdev.babenko.ui.base.BasePresenter
import com.icerockdev.babenko.utils.RxUtils

import java.util.concurrent.TimeoutException

/**
 * Created by Roman Babenko on 06/05/17.
 */
@InjectViewState
class HomePresenter(private val mHomeInteractor: HomeInteractor) : BasePresenter<HomeView>() {

    fun requestDataClicked(url: String) {
        viewState.showProgressDialog()
        mHomeInteractor.requestDataFields(url)
            .compose<Array<DataField>>(RxUtils.applyIoMainThreadSchedulersToSingle<Array<DataField>>())
            .doFinally { viewState.dismissProgressDialog() }
            .subscribe({ dataFields -> viewState.gotDataFields(dataFields) }
            ) { throwable ->
                if (throwable is IncorrectEmailException) {
                    viewState.showUrlError()
                } else if (throwable is TimeoutException) {
                    viewState.showTimeoutError()
                } else {
                    viewState.showErrorDialog()
                }
            }
    }

}


