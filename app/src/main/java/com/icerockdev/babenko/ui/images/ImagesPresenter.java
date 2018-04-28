package com.icerockdev.babenko.ui.images;

import com.arellomobile.mvp.InjectViewState;
import com.icerockdev.babenko.ui.BasePresenter;
import com.icerockdev.babenko.utils.RxUtils;

import timber.log.Timber;

/**
 * Created by Roman Babenko on 10/05/17.
 */
@InjectViewState
public class ImagesPresenter extends BasePresenter<ImagesView> {
    private static final String TAG = ImagesPresenter.class.getName();
    private ImagesInteractor mManager;

    public ImagesPresenter(ImagesInteractor manager) {
        mManager = manager;
    }

    @Override
    public void onFirstViewAttach() {
        super.onFirstViewAttach();
        requestPictures();
    }

    private void requestPictures() {
        getViewState().showProgressDialog();
        mManager.requestPicturesList()
                .compose(RxUtils.applyIoMainThreadSchedulersToSingle())
                .doFinally(() -> getViewState().dismissProgressDialog())
                .subscribe(imageItems -> {
                    Timber.tag(TAG).d("Images list length is " + imageItems.size());
                    if (imageItems.size() == 0) {
                        getViewState().showListIsEmptyError();
                        return;
                    }
                    getViewState().showImagesList(imageItems);
                }, throwable -> {
                    getViewState().showErrorDialog();
                });
    }

}
