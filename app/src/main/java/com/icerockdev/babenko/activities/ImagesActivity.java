package com.icerockdev.babenko.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.icerockdev.babenko.R;
import com.icerockdev.babenko.interfaces.ImagesView;
import com.icerockdev.babenko.presenters.ImagesPresenter;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public class ImagesActivity extends AppCompatActivity implements ImagesView{

    private ImagesPresenter mPresenter;

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
        RecyclerView imagesRecyclerView = (RecyclerView) findViewById(R.id.imagesRecyclerView);
        imagesRecyclerView.setHasFixedSize(true);
    }

    private void requestPictures() {
        mPresenter.requestPictures();
    }
}
