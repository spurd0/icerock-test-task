package com.icerockdev.babenko.ui.full_screen_image

import android.widget.ImageView

import com.arellomobile.mvp.InjectViewState
import com.icerockdev.babenko.ui.base.BasePresenter
import com.squareup.picasso.Callback

/**
 * Created by Roman Babenko on 14/05/17.
 */
@InjectViewState
class FullScreenImagePresenter(private val mImageUrl: String?, private val mManager: FullScreenImageInteractor) :
    BasePresenter<FullScreenImageView>() {

    fun requestImage(targetIv: ImageView) {
        if (mImageUrl == null) {
            throw NullPointerException("Image url is null")
        }
        viewState.showProgressDialog()
        mManager.requestImage(targetIv, mImageUrl, object : Callback {
            override fun onSuccess() {
                viewState.dismissProgressDialog()
                viewState.makeImageVisible()
            }

            override fun onError(e: Exception) {
                viewState.dismissProgressDialog()
            }
        })
    }
}
