package com.icerockdev.babenko.ui.images;

import com.icerockdev.babenko.model.entities.ImageItem;
import com.icerockdev.babenko.ui.base.views.ProgressBaseView;

import java.util.List;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public interface ImagesView extends ProgressBaseView {
    void showErrorDialog();

    void showListIsEmptyError();

    void showImagesList(List<ImageItem> imageItems);
}
