package com.icerockdev.babenko.interfaces;

import com.icerockdev.babenko.model.ImageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public interface ImagesView extends BaseView {
    void showErrorDialog(String message);

    void showListIsEmptyError();

    void showProgressDialog();

    void dismissProgressDialog();

    void gotImagesList(ArrayList<ImageItem> imageItems);
}
