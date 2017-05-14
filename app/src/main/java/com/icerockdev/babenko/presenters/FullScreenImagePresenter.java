package com.icerockdev.babenko.presenters;

import android.widget.ImageView;

import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.interfaces.FullScreenImageView;
import com.icerockdev.babenko.managers.FullScreenImageManager;
import com.squareup.picasso.Callback;

/**
 * Created by Roman Babenko on 14/05/17.
 */

public class FullScreenImagePresenter extends BasePresenter<FullScreenImageView> {
    private String mImageUrl;
    private FullScreenImageManager mManager;

    public FullScreenImagePresenter(String imageUrl, FullScreenImageManager manager) {
        mImageUrl = imageUrl;
        mManager = manager;
    }

    @Override
    public void attachView(FullScreenImageView fullScreenImageView) {
        super.attachView(fullScreenImageView);
        requestImage(fullScreenImageView.getIvForPicture());
    }

    private void requestImage(ImageView targetIv) {
        if (mImageUrl == null)
            throw new NullPointerException("Image url is null");
        getView().showProgressDialog();
        mManager.requestImage(targetIv, mImageUrl,
                new Callback() {
                    @Override
                    public void onSuccess() {
                        if (getView() != null) {
                            getView().dismissProgressDialog();
                            getView().makeImageVisible();
                        }
                    }

                    @Override
                    public void onError() {
                        if (getView() != null)
                            getView().dismissProgressDialog();
                    }
                });
    }
}
