package com.icerockdev.babenko.interfaces;

import com.icerockdev.babenko.ui.BaseView;

/**
 * Created by Roman Babenko on 14/05/17.
 */

public interface ProgressBaseView extends BaseView {
    void showProgressDialog();

    void dismissProgressDialog();
}
