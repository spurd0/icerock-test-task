package com.icerockdev.babenko.ui.images;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.interfaces.ImagesListCallback;
import com.icerockdev.babenko.managers.impl.ImagesManagerImpl;
import com.icerockdev.babenko.model.ImageItem;
import com.icerockdev.babenko.ui.BaseProgressActivity;
import com.icerockdev.babenko.ui.fragments.ServerErrorDialogFragment;
import com.icerockdev.babenko.ui.full_screen_image.FullScreenImageActivity;

import java.util.ArrayList;

import static com.icerockdev.babenko.managers.impl.ImagesManagerImpl.CODE_ERROR_LIST_NULL_RESPONSE;
import static com.icerockdev.babenko.managers.impl.ImagesManagerImpl.CODE_ERROR_OTHER;
import static com.icerockdev.babenko.ui.fragments.ServerErrorDialogFragment.DIALOG_MESSAGE_KEY;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public class ImagesActivity extends BaseProgressActivity implements ImagesView {
    private static final String SERVER_ERROR_DIALOG_TAG = "com.icerockdev.babenko.ui.images_activity.ImagesActivity.SERVER_ERROR_DIALOG_TAG";
    private static final String TAG = "ImagesActivity";
    @InjectPresenter
    ImagesPresenter mPresenter;
    private RecyclerView mImagesRecyclerView;
    private TextView mListIsEmptyErrorTv;
    private ArrayList<ImageItem> mImagesList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        initViews();
    }

    @ProvidePresenter
    ImagesPresenter provideImagesPresenter() {
        return new ImagesPresenter(new ImagesManagerImpl());
    }

    @Override
    protected void setDialogFragmentTag() {
        mDialogTag = "com.icerockdev.babenko.ui.images_activity.ImagesActivity.PROGRESS_DIALOG_TAG";
    }

    private void initViews() {
        mImagesRecyclerView = (RecyclerView) findViewById(R.id.imagesRecyclerView);
        mListIsEmptyErrorTv = (TextView) findViewById(R.id.imagesListEmptyTv);
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
        mListIsEmptyErrorTv.setVisibility(View.VISIBLE);
        mImagesRecyclerView.setVisibility(View.GONE);
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
        mListIsEmptyErrorTv.setVisibility(View.GONE);
        mImagesRecyclerView.setVisibility(View.VISIBLE);
        mImagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mImagesRecyclerView.setAdapter(adapter);
    }
}
