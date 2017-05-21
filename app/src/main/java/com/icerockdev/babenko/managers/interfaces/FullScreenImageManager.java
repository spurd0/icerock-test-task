package com.icerockdev.babenko.managers.interfaces;

import android.widget.ImageView;

/**
 * Created by Roman Babenko on 14/05/17.
 */

public interface FullScreenImageManager {

    void requestImage(ImageView targetIv, String imageUrl,
                      com.squareup.picasso.Callback imageCallback);

}
