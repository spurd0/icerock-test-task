package com.icerockdev.babenko.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.adapters.ImagesAdapter;
import com.icerockdev.babenko.fragments.ProgressDialogFragment;
import com.icerockdev.babenko.fragments.ServerErrorDialogFragment;
import com.icerockdev.babenko.interfaces.ImagesListCallback;
import com.icerockdev.babenko.interfaces.ImagesView;
import com.icerockdev.babenko.model.ImageItem;
import com.icerockdev.babenko.presenters.ImagesPresenter;

import java.util.ArrayList;

import static com.icerockdev.babenko.fragments.ServerErrorDialogFragment.DIALOG_MESSAGE_KEY;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public class ImagesActivity extends AppCompatActivity implements ImagesView {
    private static final String SERVER_ERROR_DIALOG_TAG = "com.icerockdev.babenko.activities.ImagesActivity.SERVER_ERROR_DIALOG_TAG";
    private static final String PROGRESS_DIALOG_TAG = "com.icerockdev.babenko.activities.ImagesActivity.PROGRESS_DIALOG_TAG";
    private static final String TAG = "ImagesActivity";
    private ImagesPresenter mPresenter;
    private RecyclerView mImagesRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        initViews();
        mPresenter = new ImagesPresenter();
        mPresenter.attachView(this);
        requestPictures();

    }

    private void initViews() {
        mImagesRecyclerView = (RecyclerView) findViewById(R.id.imagesRecyclerView);
        mImagesRecyclerView.setHasFixedSize(true);
    }

    private void requestPictures() {
        mPresenter.requestPictures();
    }

    @Override
    public void showProgressDialog() {
        ProgressDialogFragment progressDialogFragment = new ProgressDialogFragment();
        progressDialogFragment.show(getSupportFragmentManager(), PROGRESS_DIALOG_TAG);
    }

    @Override
    public void dismissProgressDialog() {
        ProgressDialogFragment progressDialogFragment = (ProgressDialogFragment) getSupportFragmentManager().
                findFragmentByTag(PROGRESS_DIALOG_TAG);
        if (progressDialogFragment != null)
            progressDialogFragment.dismiss();
    }

    @Override
    public void showErrorDialog(String error) {
        ServerErrorDialogFragment serverErrorDialogFragment = new ServerErrorDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putString(DIALOG_MESSAGE_KEY, error);
        serverErrorDialogFragment.setArguments(arguments);
        serverErrorDialogFragment.show(getSupportFragmentManager(), SERVER_ERROR_DIALOG_TAG);
    }


    @Override
    public void gotImagesList(ArrayList<ImageItem> images) {
        ImagesAdapter adapter = new ImagesAdapter(this, images, new ImagesListCallback() {
            @Override
            public void itemClicked(String imageUrl) {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "Image for view is " + imageUrl);
            }
        });
        mImagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mImagesRecyclerView.setAdapter(adapter);
    }
}
