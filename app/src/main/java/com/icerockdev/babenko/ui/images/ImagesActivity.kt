package com.icerockdev.babenko.ui.images

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.icerockdev.babenko.IceRockApplication
import com.icerockdev.babenko.R
import com.icerockdev.babenko.model.entities.ImageItem
import com.icerockdev.babenko.repo.ImageRepository
import com.icerockdev.babenko.ui.base.activities.BaseProgressActivity
import com.icerockdev.babenko.ui.base.fragments.ServerErrorDialogFragment
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by Roman Babenko on 10/05/17.
 */

class ImagesActivity : BaseProgressActivity(), ImagesView {
    @InjectPresenter
    lateinit var mPresenter: ImagesPresenter

    @Inject
    lateinit var imageRepository: ImageRepository

    @BindView(R.id.imagesListEmptyTv)
    lateinit var imagesListEmptyTv: TextView
    @BindView(R.id.imagesRecyclerView)
    lateinit var imagesRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_images)
        ButterKnife.bind(this)
    }

    @ProvidePresenter
    internal fun provideImagesPresenter(): ImagesPresenter {
        IceRockApplication.appComponent.inject(this)
        return ImagesPresenter(ImagesInteractorImpl(imageRepository))
    }

    override fun showErrorDialog() {
        val errorMsg = getString(R.string.request_images_list_error_other)
        val serverErrorDialogFragment = ServerErrorDialogFragment.getDialog(errorMsg)
        serverErrorDialogFragment.show(supportFragmentManager, SERVER_ERROR_DIALOG_TAG)
    }

    override fun showListIsEmptyError() {
        imagesListEmptyTv.visibility = View.VISIBLE
        imagesRecyclerView.visibility = View.GONE
    }

    override fun showImagesList(images: List<ImageItem>) {
        val adapter = ImagesAdapter(images, object : ImagesListCallback {
            override fun itemClicked(imageUrl: String) {
                Timber.tag(TAG).d("Image for view is:%s", imageUrl)
                navigator.navigateToFullScreenImageActivity(this@ImagesActivity, imageUrl)
            }
        })
        imagesListEmptyTv.visibility = View.GONE
        imagesRecyclerView.visibility = View.VISIBLE
        imagesRecyclerView.layoutManager = LinearLayoutManager(this)
        imagesRecyclerView.adapter = adapter
    }

    companion object {
        private const val SERVER_ERROR_DIALOG_TAG = "ImagesActivity.SERVER_ERROR_DIALOG_TAG"
        private const val TAG = "ImagesActivity"

        fun getLaunchingIntent(context: Context): Intent {
            return Intent(context, ImagesActivity::class.java)
        }
    }
}
