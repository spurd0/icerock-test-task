package com.icerockdev.babenko.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.icerockdev.babenko.R;
import com.icerockdev.babenko.adapters.ImagesAdapter;
import com.icerockdev.babenko.interfaces.ImagesListCallback;
import com.icerockdev.babenko.interfaces.ImagesView;
import com.icerockdev.babenko.model.ImageItem;
import com.icerockdev.babenko.presenters.ImagesPresenter;

import java.util.ArrayList;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public class ImagesActivity extends AppCompatActivity implements ImagesView{

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
    public void showErrorDialog(String message) {

    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void dismissProgressDialog() {

    }

    @Override
    public void gotImagesList(ArrayList<ImageItem> images) {
        ImagesAdapter adapter = new ImagesAdapter(this, images, new ImagesListCallback() {
            @Override
            public void itemClicked(int id) {

            }
        });
        mImagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mImagesRecyclerView.setAdapter(adapter);
    }
}
