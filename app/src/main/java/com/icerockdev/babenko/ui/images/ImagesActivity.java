package com.icerockdev.babenko.ui.images;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.databinding.ActivityImagesBinding;
import com.icerockdev.babenko.interfaces.ImagesListCallback;
import com.icerockdev.babenko.models.impl.ImagesModelImpl;
import com.icerockdev.babenko.model.ImageItem;
import com.icerockdev.babenko.ui.BaseProgressActivity;
import com.icerockdev.babenko.ui.fragments.ServerErrorDialogFragment;
import com.icerockdev.babenko.ui.full_screen_image.FullScreenImageActivity;

import java.util.ArrayList;

import static com.icerockdev.babenko.models.impl.ImagesModelImpl.CODE_ERROR_LIST_NULL_RESPONSE;
import static com.icerockdev.babenko.models.impl.ImagesModelImpl.CODE_ERROR_OTHER;
import static com.icerockdev.babenko.ui.fragments.ServerErrorDialogFragment.DIALOG_MESSAGE_KEY;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public class ImagesActivity extends BaseProgressActivity implements ImagesView {
    private static final String SERVER_ERROR_DIALOG_TAG = "com.icerockdev.babenko.ui.images_activity.ImagesActivity.SERVER_ERROR_DIALOG_TAG";
    private static final String TAG = "ImagesActivity";
    @InjectPresenter
    ImagesPresenter mPresenter;
    private ActivityImagesBinding mBinding;
    private ArrayList<ImageItem> mImagesList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
    }

    @ProvidePresenter
    ImagesPresenter provideImagesPresenter() {
        return new ImagesPresenter(new ImagesModelImpl());
    }

    @Override
    protected void setDialogFragmentTag() {
        mDialogTag = "com.icerockdev.babenko.ui.images_activity.ImagesActivity.PROGRESS_DIALOG_TAG";
    }

    @Override
    public void showErrorDialog(int errorCode) {
        ServerErrorDialogFragment serverErrorDialogFragment = new ServerErrorDialogFragment();
        Bundle arguments = new Bundle();
        String errorMsg;
        switch (errorCode) {
            case CODE_ERROR_LIST_NULL_RESPONSE:
                errorMsg = getString(R.string.request_images_list_error_null);
                break;
            case CODE_ERROR_OTHER:
                errorMsg = getString(R.string.request_images_list_error_other);
                break;
            default:
                errorMsg = getString(R.string.request_images_list_error_other);
                break;
        }
        arguments.putString(DIALOG_MESSAGE_KEY, errorMsg);
        serverErrorDialogFragment.setArguments(arguments);
        serverErrorDialogFragment.show(getSupportFragmentManager(), SERVER_ERROR_DIALOG_TAG);
    }

    @Override
    public void showListIsEmptyError() {
        mBinding.imagesListEmptyTv.setVisibility(View.VISIBLE);
        mBinding.imagesRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showImagesList(ArrayList<ImageItem> images) {
        if (mImagesList != null)
            return;
        mImagesList = images;
        ImagesAdapter adapter = new ImagesAdapter(mImagesList, new ImagesListCallback() {
            @Override
            public void itemClicked(String imageUrl) {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "Image for view is " + imageUrl);
                FullScreenImageActivity.startActivity(ImagesActivity.this, imageUrl);
            }
        });
        mBinding.imagesListEmptyTv.setVisibility(View.GONE);
        mBinding.imagesRecyclerView.setVisibility(View.VISIBLE);
        mBinding.imagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.imagesRecyclerView.setAdapter(adapter);
    }
}
