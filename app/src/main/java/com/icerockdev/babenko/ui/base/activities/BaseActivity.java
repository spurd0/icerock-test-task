package com.icerockdev.babenko.ui.base.activities;

import android.os.Bundle;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.icerockdev.babenko.ui.navigation.Navigator;

/**
 * Created by Roman Babenko on 30/07/17.
 */

public abstract class BaseActivity extends MvpAppCompatActivity {
    protected Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navigator = new Navigator();
    }
}
