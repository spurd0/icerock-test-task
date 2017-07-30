package com.icerockdev.babenko.ui.full_screen_image;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.managers.impl.FullScreenImageManagerImpl;
import com.icerockdev.babenko.ui.BaseProgressActivity;

/**
 * Created by Roman Babenko on 5/11/2017.
 */

public class FullScreenImageActivity extends BaseProgressActivity implements FullScreenImageView, View.OnTouchListener {
    public static final String IMAGE_URL_KEY = "com.icerockdev.babenko.ui.full_screen_image.FullScreenImagePresenter.IMAGE_URL_KEY";
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    @InjectPresenter
    FullScreenImagePresenter mPresenter;
    private ImageView mImageView;
    private Matrix mMatrix = new Matrix();
    private Matrix mSavedMatrix = new Matrix();
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
        initViews();
    }

    @ProvidePresenter
    FullScreenImagePresenter provideFullScreenImagePresenter() {
        return new FullScreenImagePresenter(getIntent().getStringExtra(IMAGE_URL_KEY),
                new FullScreenImageManagerImpl());
    }

    @Override
    protected void setDialogFragmentTag() {
        mDialogTag = "com.icerockdev.babenko.ui.full_image.FullScreenImageActivity.PROGRESS_DIALOG_TAG";
    }

    private void initViews() {
        mImageView = (ImageView) findViewById(R.id.fullScreenImageView);
        mImageView.setOnTouchListener(this);
        mPresenter.requestImage(mImageView);
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
        mPresenter.detachView();
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.attachView(this);
    }

    @Override
    public void makeImageVisible() {
        mImageView.setVisibility(View.VISIBLE);
    }
}
