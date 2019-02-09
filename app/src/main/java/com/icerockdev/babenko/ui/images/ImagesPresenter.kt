package com.icerockdev.babenko.ui.images

import com.arellomobile.mvp.InjectViewState
import com.icerockdev.babenko.applicaiton.utils.applyIoMainThreadSchedulersToSingle
import com.icerockdev.babenko.model.entities.ImageItem
import com.icerockdev.babenko.ui.base.BasePresenter
import timber.log.Timber

/**
 * Created by Roman Babenko on 10/05/17.
 */
@InjectViewState
class ImagesPresenter(private val mManager: ImagesInteractor) : BasePresenter<ImagesView>() {

    public override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        requestPictures()
    }

    private fun requestPictures() {
        viewState.showProgressDialog()
        mManager.requestPicturesList()
            .compose<List<ImageItem>>(applyIoMainThreadSchedulersToSingle<List<ImageItem>>())
            .doFinally { viewState.dismissProgressDialog() }
            .subscribe({ imageItems ->
                Timber.tag(TAG).d("Images list length is:%s", imageItems.size)
                if (imageItems.size == 0) {
                    viewState.showListIsEmptyError()
                } else {
                    viewState.showImagesList(imageItems)
                }
            }) { throwable -> viewState.showErrorDialog() }
    }

    companion object {
        private val TAG = "ImagesPresenter"
    }

}
