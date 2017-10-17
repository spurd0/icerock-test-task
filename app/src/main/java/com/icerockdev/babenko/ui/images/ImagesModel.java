package com.icerockdev.babenko.ui.images;

import com.icerockdev.babenko.model.entities.ImageItem;

import java.util.ArrayList;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public interface ImagesModel {
    void requestPicturesList(final ImagesCallback callback);

    interface ImagesCallback {
        void successResponse(ArrayList<ImageItem> images);

        void failedResponse(int errorCode);
    }

}
