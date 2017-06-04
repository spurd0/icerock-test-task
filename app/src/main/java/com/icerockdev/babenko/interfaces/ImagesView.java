package com.icerockdev.babenko.interfaces;

import com.icerockdev.babenko.model.ImageItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public interface ImagesView extends ProgressBaseView {
    void showErrorDialog(int errorCode);

    void showListIsEmptyError();

    void showImagesList(ArrayList<ImageItem> imageItems);
}
