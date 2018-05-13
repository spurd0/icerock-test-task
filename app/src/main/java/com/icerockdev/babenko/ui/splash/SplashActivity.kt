package com.icerockdev.babenko.ui.splash

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.icerockdev.babenko.IceRockApplication
import com.icerockdev.babenko.R
import com.icerockdev.babenko.managers.SharedPreferencesManager
import com.icerockdev.babenko.ui.base.activities.BaseActivity
import javax.inject.Inject

class SplashActivity : BaseActivity(), SplashView {
    @InjectPresenter
    lateinit var presenter: SplashPresenter

    @Inject
    lateinit var manager: SharedPreferencesManager

    @BindView(R.id.splash_image_view)
    lateinit var splashImageView: ImageView
    @BindView(R.id.container)
    lateinit var container: FrameLayout

    private var animation: Animation? = null

    @ProvidePresenter
    internal fun provideSplashPresenter(): SplashPresenter {
        IceRockApplication.appComponent.inject(this)
        return SplashPresenter(manager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash_screen)
        ButterKnife.bind(this)
    }

    override fun onResume() {
        super.onResume()
        if (animation == null) {
            animation = AnimationUtils.loadAnimation(
                    applicationContext, R.anim.center_to_top_move_animation)
            animation!!.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation) {

                }

                override fun onAnimationEnd(a: Animation) {
                    presenter.animationFinished()
                    splashImageView.visibility = View.GONE
                }

                override fun onAnimationRepeat(animation: Animation) {

                }
            })
            splashImageView.startAnimation(animation)
        }
    }

    override fun startImagesActivity() {
        navigator.navigateToImagesActivity(this)
        finish()
    }

    override fun startLoginActivity() {
        navigator.navigateToLoginActivity(this)
        finish()
    }
}
