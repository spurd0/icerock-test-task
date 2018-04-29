package com.icerockdev.babenko.ui.images;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.model.entities.ImageItem;
import com.icerockdev.babenko.repo.ImageRepository;
import com.icerockdev.babenko.ui.base.activities.BaseProgressActivity;
import com.icerockdev.babenko.ui.base.fragments.ServerErrorDialogFragment;
import com.icerockdev.babenko.ui.full_screen_image.FullScreenImageActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.icerockdev.babenko.ui.base.fragments.ServerErrorDialogFragment.DIALOG_MESSAGE_KEY;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public class ImagesActivity extends BaseProgressActivity implements ImagesView {
    private static final String SERVER_ERROR_DIALOG_TAG = "ImagesActivity.SERVER_ERROR_DIALOG_TAG";
    private static final String TAG = "ImagesActivity";
    @InjectPresenter
    ImagesPresenter mPresenter;

    @Inject
    ImageRepository imageRepository;

    @BindView(R.id.imagesListEmptyTv)
    TextView imagesListEmptyTv;
    @BindView(R.id.imagesRecyclerView)
    RecyclerView imagesRecyclerView;

    public static Intent getLaunchingIntent(Context context) {
        Intent intent = new Intent(context, ImagesActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        ButterKnife.bind(this);
    }

    @ProvidePresenter
    ImagesPresenter provideImagesPresenter() {
        IceRockApplication.getAppComponent().inject(this);
        return new ImagesPresenter(new ImagesInteractorImpl(imageRepository));
    }

    @Override
    protected void setDialogFragmentTag() {
        mDialogTag = "ImagesActivity.PROGRESS_DIALOG_TAG";
    }

    @Override
    public void showErrorDialog() {
        ServerErrorDialogFragment serverErrorDialogFragment = new ServerErrorDialogFragment();
        Bundle arguments = new Bundle();
        String errorMsg = getString(R.string.request_images_list_error_other);
        arguments.putString(DIALOG_MESSAGE_KEY, errorMsg);
        serverErrorDialogFragment.setArguments(arguments);
        serverErrorDialogFragment.show(getSupportFragmentManager(), SERVER_ERROR_DIALOG_TAG);
    }

    @Override
    public void showListIsEmptyError() {
        imagesListEmptyTv.setVisibility(View.VISIBLE);
        imagesRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showImagesList(final List<ImageItem> images) {
        ImagesAdapter adapter = new ImagesAdapter(images, imageUrl -> {

            Timber.tag(TAG).d("Image for view is:%s", imageUrl);
            FullScreenImageActivity.startActivity(ImagesActivity.this, imageUrl);
        });
        imagesListEmptyTv.setVisibility(View.GONE);
        imagesRecyclerView.setVisibility(View.VISIBLE);
        imagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        imagesRecyclerView.setAdapter(adapter);
    }
}
