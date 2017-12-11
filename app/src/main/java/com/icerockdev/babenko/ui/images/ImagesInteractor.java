package com.icerockdev.babenko.ui.images;

import com.icerockdev.babenko.model.entities.ImageItem;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public interface ImagesInteractor {
    Single<List<ImageItem>> requestPicturesList();
}
