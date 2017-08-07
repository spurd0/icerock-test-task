package com.icerockdev.babenko.models.interfaces;

import android.widget.ImageView;

/**
 * Created by Roman Babenko on 14/05/17.
 */

public interface FullScreenImageModel {

    void requestImage(ImageView targetIv, String imageUrl,
                      com.squareup.picasso.Callback imageCallback);

}
