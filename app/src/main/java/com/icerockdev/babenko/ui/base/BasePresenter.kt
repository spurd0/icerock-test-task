package com.icerockdev.babenko.ui.base

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView

/**
 * Created by Roman Babenko on 06/05/17.
 */

abstract class BasePresenter<View : MvpView> : MvpPresenter<View>()
