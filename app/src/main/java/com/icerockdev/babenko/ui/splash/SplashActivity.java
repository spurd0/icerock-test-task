package com.icerockdev.babenko.ui.splash;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.managers.SharedPreferencesManager;
import com.icerockdev.babenko.ui.base.activities.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements SplashView {
    @InjectPresenter
    SplashPresenter presenter;

    @Inject
    SharedPreferencesManager manager;

    @BindView(R.id.splash_image_view)
    ImageView splashImageView;
    @BindView(R.id.container)
    FrameLayout container;
    private Animation animation;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animation == null) {
            animation = AnimationUtils.loadAnimation(
                    getApplicationContext(), R.anim.center_to_top_move_animation);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation a) {
                    presenter.animationFinished();
                    splashImageView.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            splashImageView.startAnimation(animation);
        }
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
