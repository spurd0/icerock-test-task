package com.icerockdev.babenko.ui.base.activities

import com.arellomobile.mvp.MvpAppCompatActivity
import com.icerockdev.babenko.ui.navigation.Navigator

/**
 * Created by Roman Babenko on 30/07/17.
 */

abstract class BaseActivity : MvpAppCompatActivity() {
    protected var navigator: Navigator = Navigator()
}
