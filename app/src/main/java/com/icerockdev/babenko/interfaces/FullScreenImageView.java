package com.icerockdev.babenko.interfaces;

import android.widget.ImageView;

/**
 * Created by Roman Babenko on 14/05/17.
 */

public interface FullScreenImageView extends BaseView {
    ImageView getIvForPicture();

    void showProgressDialog();

    void hideProgressDialog();

    void makeImageVisible();
}
