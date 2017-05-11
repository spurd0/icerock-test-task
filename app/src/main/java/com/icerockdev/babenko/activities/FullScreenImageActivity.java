package com.icerockdev.babenko.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.interfaces.BaseView;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by Roman Babenko on 5/11/2017.
 */

public class FullScreenImageActivity extends AppCompatActivity implements BaseView {
    public static final String IMAGE_URL_KEY = "com.icerockdev.babenko.presenters.FullScreenImagePresenter.IMAGE_URL_KEY";

    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: 5/11/2017 make fullscreen
        setContentView(R.layout.activity_full_screen_image);
        initViews();
        requestImage();
    }

    private void initViews() {
        mImageView = (ImageView) findViewById(R.id.fullScreenImageView);
    }

    private void requestImage() {
        String imageUrl = getIntent().getStringExtra(IMAGE_URL_KEY);
        if (imageUrl == null)
            throw new NullPointerException("Image url is null");
        Picasso picasso = new Picasso.Builder(this).listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                if (BuildConfig.DEBUG) exception.printStackTrace();
            }
        }).downloader(new OkHttpDownloader(this)).build();
        picasso.load(imageUrl).into(mImageView);
    }
}
