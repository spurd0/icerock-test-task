package com.icerockdev.babenko.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.icerockdev.babenko.R;

/**
 * Created by Roman Babenko on 5/11/2017.
 */

public class FullScreenImageActivity extends AppCompatActivity {
    private ImageView mImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: 5/11/2017 make fullscreen
        setContentView(R.layout.activity_full_screen_image);
    }

    private void initViews() {
        mImageView = (ImageView) findViewById(R.id.fullScreenImageView);
    }
}
