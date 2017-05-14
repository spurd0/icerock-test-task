package com.icerockdev.babenko.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.icerockdev.babenko.R;
import com.icerockdev.babenko.interfaces.FullScreenImageView;
import com.icerockdev.babenko.presenters.FullScreenImagePresenter;

/**
 * Created by Roman Babenko on 5/11/2017.
 */

public class FullScreenImageActivity extends BaseProgressActivity implements FullScreenImageView, View.OnTouchListener {
    public static final String IMAGE_URL_KEY = "com.icerockdev.babenko.presenters.FullScreenImagePresenter.IMAGE_URL_KEY";

    private ImageView mImageView;
    private FullScreenImagePresenter mPresenter;

    private Matrix mMatrix = new Matrix();
    private Matrix mSavedMatrix = new Matrix();
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mMode = NONE;
    private PointF mStart = new PointF();
    private PointF mMid = new PointF();
    private float mOldDist = 1f;

    public static void startActivity(Context context, String imageUrl) {
        Intent intent = new Intent(context, FullScreenImageActivity.class);
        intent.putExtra(IMAGE_URL_KEY, imageUrl);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        mPresenter = new FullScreenImagePresenter(getIntent().getStringExtra(IMAGE_URL_KEY));
        initViews();
    }

    @Override
    protected void setDialogFragmentTag() {
        mDialogTag = "com.icerockdev.babenko.activities.FullScreenImageActivity.PROGRESS_DIALOG_TAG";
    }

    private void initViews() {
        mImageView = (ImageView) findViewById(R.id.fullScreenImageView);
        mImageView.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView view = (ImageView) v;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mSavedMatrix.set(mMatrix);
                mStart.set(event.getX(), event.getY());
                mMode = DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mOldDist = spacing(event);
                if (mOldDist > 10f) {
                    mSavedMatrix.set(mMatrix);
                    midPoint(mMid, event);
                    mMode = ZOOM;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mMode = NONE;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mMode == DRAG) {
                    mMatrix.set(mSavedMatrix);
                    mMatrix.postTranslate(event.getX() - mStart.x, event.getY()
                            - mStart.y);
                } else if (mMode == ZOOM) {
                    float newDist = spacing(event);
                    if (newDist > 10f) {
                        mMatrix.set(mSavedMatrix);
                        float scale = newDist / mOldDist;
                        mMatrix.postScale(scale, scale, mMid.x, mMid.y);
                    }
                }
                break;
        }

        view.setImageMatrix(mMatrix);
        return true;
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.detachView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.attachView(this);
    }

    @Override
    public ImageView getIvForPicture() {
        return mImageView;
    }

    @Override
    public void showProgressDialog() {

    }

    @Override
    public void dismissProgressDialog() {

    }


    @Override
    public void makeImageVisible() {
        mImageView.setVisibility(View.VISIBLE);
    }
}
