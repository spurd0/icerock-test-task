package com.icerockdev.babenko.interfaces;

import com.icerockdev.babenko.model.DataField;
import com.icerockdev.babenko.model.ImageResponse;

import java.util.ArrayList;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public interface ImagesView extends BaseView {
    void showErrorDialog(String message); // TODO: 10/05/17 move to base class

    void showProgressDialog();

    void dismissProgressDialog();

    void gotImagesList(ArrayList<ImageResponse> images);
}
