package com.icerockdev.babenko.ui.full_screen_image

import android.content.Context
import android.content.Intent
import android.graphics.Matrix
import android.graphics.PointF
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.icerockdev.babenko.R
import com.icerockdev.babenko.ui.base.activities.BaseProgressActivity

/**
 * Created by Roman Babenko on 5/11/2017.
 */

class FullScreenImageActivity : BaseProgressActivity(), FullScreenImageView, View.OnTouchListener {

    @InjectPresenter
    lateinit var mPresenter: FullScreenImagePresenter

    @BindView(R.id.fullScreenImageView)
    lateinit var fullScreenImageView: ImageView

    private val mMatrix = Matrix()
    private val mSavedMatrix = Matrix()
    private var mMode = NONE
    private val mStart = PointF()
    private val mMid = PointF()
    private var mOldDist = 1f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image)
        ButterKnife.bind(this)

        initViews()
    }

    @ProvidePresenter
    internal fun provideFullScreenImagePresenter(): FullScreenImagePresenter {
        return FullScreenImagePresenter(
            intent.getStringExtra(IMAGE_URL_KEY),
            FullScreenImageInteractorImpl()
        )
    }

    private fun initViews() {
        fullScreenImageView.setOnTouchListener(this)
        mPresenter.requestImage(fullScreenImageView)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        val view = v as ImageView

        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                mSavedMatrix.set(mMatrix)
                mStart.set(event.x, event.y)
                mMode = DRAG
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                mOldDist = spacing(event)
                if (mOldDist > 10f) {
                    mSavedMatrix.set(mMatrix)
                    midPoint(mMid, event)
                    mMode = ZOOM
                }
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> mMode = NONE
            MotionEvent.ACTION_MOVE -> if (mMode == DRAG) {
                mMatrix.set(mSavedMatrix)
                mMatrix.postTranslate(event.x - mStart.x, event.y - mStart.y)
            } else if (mMode == ZOOM) {
                val newDist = spacing(event)
                if (newDist > 10f) {
                    mMatrix.set(mSavedMatrix)
                    val scale = newDist / mOldDist
                    mMatrix.postScale(scale, scale, mMid.x, mMid.y)
                }
            }
        }

        view.imageMatrix = mMatrix
        return true
    }

    private fun spacing(event: MotionEvent): Float {
        val x = event.getX(0) - event.getX(1)
        val y = event.getY(0) - event.getY(1)
        return Math.sqrt((x * x + y * y).toDouble()).toFloat()
    }

    private fun midPoint(point: PointF, event: MotionEvent) {
        val x = event.getX(0) + event.getX(1)
        val y = event.getY(0) + event.getY(1)
        point.set(x / 2, y / 2)
    }

    override fun makeImageVisible() {
        fullScreenImageView.visibility = View.VISIBLE
    }

    companion object {
        val IMAGE_URL_KEY = "FullScreenImagePresenter.IMAGE_URL_KEY"
        private val NONE = 0
        private val DRAG = 1
        private val ZOOM = 2

        fun getStartingIntent(context: Context, imageUrl: String): Intent {
            val intent = Intent(context, FullScreenImageActivity::class.java)
            intent.putExtra(IMAGE_URL_KEY, imageUrl)
            return intent
        }
    }
}
