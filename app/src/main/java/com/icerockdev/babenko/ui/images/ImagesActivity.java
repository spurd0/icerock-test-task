package com.icerockdev.babenko.ui.images;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.core.NetworkApi;
import com.icerockdev.babenko.databinding.ActivityImagesBinding;
import com.icerockdev.babenko.model.entities.ImageItem;
import com.icerockdev.babenko.repo.impl.ImageRepositoryImpl;
import com.icerockdev.babenko.ui.BaseProgressActivity;
import com.icerockdev.babenko.ui.fragments.ServerErrorDialogFragment;
import com.icerockdev.babenko.ui.full_screen_image.FullScreenImageActivity;

import java.util.List;

import javax.inject.Inject;

import static com.icerockdev.babenko.ui.fragments.ServerErrorDialogFragment.DIALOG_MESSAGE_KEY;

/**
 * Created by Roman Babenko on 10/05/17.
 */

public class ImagesActivity extends BaseProgressActivity implements ImagesView {
    private static final String SERVER_ERROR_DIALOG_TAG = ImagesActivity.class.getName() + ".SERVER_ERROR_DIALOG_TAG";
    private static final String TAG = ImagesActivity.class.getName();
    @InjectPresenter
    ImagesPresenter mPresenter;
    @Inject
    NetworkApi mNetworkApi;
    private ActivityImagesBinding mBinding;

    public static void start(Context context) {
        Intent intent = new Intent(context, ImagesActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_images);
    }

    @ProvidePresenter
    ImagesPresenter provideImagesPresenter() {
        IceRockApplication.getAppComponent().inject(this);
        return new ImagesPresenter(new ImagesInteractorImpl(new ImageRepositoryImpl(mNetworkApi), this));
    }

    @Override
    protected void setDialogFragmentTag() {
        mDialogTag = ImagesActivity.class.getName() + ".PROGRESS_DIALOG_TAG";
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
        mBinding.imagesListEmptyTv.setVisibility(View.VISIBLE);
        mBinding.imagesRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showImagesList(final List<ImageItem> images) {
        ImagesAdapter adapter = new ImagesAdapter(images, imageUrl -> {
            if (BuildConfig.DEBUG)
                Log.d(TAG, "Image for view is " + imageUrl);
            FullScreenImageActivity.startActivity(ImagesActivity.this, imageUrl);
        });
        mBinding.imagesListEmptyTv.setVisibility(View.GONE);
        mBinding.imagesRecyclerView.setVisibility(View.VISIBLE);
        mBinding.imagesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBinding.imagesRecyclerView.setAdapter(adapter);
    }
}
