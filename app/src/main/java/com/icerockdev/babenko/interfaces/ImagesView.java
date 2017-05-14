package com.icerockdev.babenko.interfaces;

import com.icerockdev.babenko.model.ImageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public interface ImagesView extends ProgressBaseView {
    void showErrorDialog(String message);

    void showListIsEmptyError();

    void gotImagesList(ArrayList<ImageItem> imageItems);
}
