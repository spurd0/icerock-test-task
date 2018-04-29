package com.icerockdev.babenko.ui.splash;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.managers.SharedPreferencesManager;
import com.icerockdev.babenko.ui.base.activities.BaseActivity;
import com.icerockdev.babenko.utils.UtilsHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements SplashView {
    public static final int ANIMATION_DURATION = 500;
    @InjectPresenter
    SplashPresenter presenter;

    @Inject
    SharedPreferencesManager manager;

    @BindView(R.id.splash_image_view)
    ImageView splashImageView;
    @BindView(R.id.container)
    FrameLayout container;

    @ProvidePresenter
    SplashPresenter provideSplashPresenter() {
        IceRockApplication.getAppComponent().inject(this);
        return new SplashPresenter(manager);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        ButterKnife.bind(this);


        splashImageView.postDelayed(() -> {
            final AnimatorSet animatorSet = UtilsHelper.getMoveScalingAnimator(container,
                    splashImageView, splashImageView.getRootView(),
                    0, 0, 300, 0);
            animatorSet.start();
            animatorSet.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    presenter.animationFinished();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }, ANIMATION_DURATION);

    }

    @Override
    public void startImagesActivity() {
        navigator.navigateToImagesActivity(this);
        finish();
    }

    @Override
    public void startLoginActivity() {
        navigator.navigateToLoginActivity(this);
        finish();
    }
}
