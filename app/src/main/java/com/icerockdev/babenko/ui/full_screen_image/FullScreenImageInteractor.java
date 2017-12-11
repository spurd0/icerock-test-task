package com.icerockdev.babenko.ui.full_screen_image;

import android.widget.ImageView;

/**
 * Created by Roman Babenko on 14/05/17.
 */

public interface FullScreenImageInteractor {

    void requestImage(ImageView targetIv, String imageUrl,
                      com.squareup.picasso.Callback imageCallback);

}
