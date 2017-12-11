package com.icerockdev.babenko.ui.full_screen_image;

import android.widget.ImageView;

import com.arellomobile.mvp.InjectViewState;
import com.icerockdev.babenko.ui.BasePresenter;
import com.squareup.picasso.Callback;

/**
 * Created by Roman Babenko on 14/05/17.
 */
@InjectViewState
public class FullScreenImagePresenter extends BasePresenter<FullScreenImageView> {
    private String mImageUrl;
    private FullScreenImageInteractor mManager;

    public FullScreenImagePresenter(String imageUrl, FullScreenImageInteractor manager) {
        mImageUrl = imageUrl;
        mManager = manager;
    }

    public void requestImage(ImageView targetIv) {
        if (mImageUrl == null)
            throw new NullPointerException("Image url is null");
        if (getViewState() != null)
            getViewState().showProgressDialog();
        mManager.requestImage(targetIv, mImageUrl,
                new Callback() {
                    @Override
                    public void onSuccess() {
                        if (getViewState() != null) {
                            getViewState().dismissProgressDialog();
                            getViewState().makeImageVisible();
                        }
                    }

                    @Override
                    public void onError() {
                        if (getViewState() != null)
                            getViewState().dismissProgressDialog();
                    }
                });
    }
}
