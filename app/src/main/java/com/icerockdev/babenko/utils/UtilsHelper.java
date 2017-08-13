package com.icerockdev.babenko.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.icerockdev.babenko.R;

import static com.icerockdev.babenko.core.ApplicationConstants.EMAIL;
import static com.icerockdev.babenko.core.ApplicationConstants.NUMBER;
import static com.icerockdev.babenko.core.ApplicationConstants.PHONE;
import static com.icerockdev.babenko.core.ApplicationConstants.TEXT;
import static com.icerockdev.babenko.core.ApplicationConstants.URL;

/**
 * Created by Roman Babenko on 03/05/17.
 */

public class UtilsHelper {
    public static int convertDpToPx(Context context, float dpValue) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dpValue,
                r.getDisplayMetrics()
        );
    }

    public static int getInputType(String type) {
        switch (type) {
            case TEXT:
                return InputType.TYPE_CLASS_TEXT;
            case EMAIL:
                return InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
            case PHONE:
                return InputType.TYPE_CLASS_PHONE;
            case NUMBER:
                return InputType.TYPE_CLASS_NUMBER;
            case URL:
                return InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS;
            default:
                throw new IllegalArgumentException("Unknown type");
        }
    }

    public static String getInputHint(String type, Context context) {
        switch (type) {
            case TEXT:
                return context.getString(R.string.data_field_text_hint);
            case EMAIL:
                return context.getString(R.string.data_field_email_hint);
            case PHONE:
                return context.getString(R.string.data_field_phone_hint);
            case NUMBER:
                return context.getString(R.string.data_field_number_hint);
            case URL:
                return context.getString(R.string.data_field_url_hint);
            default:
                throw new IllegalArgumentException("Unknown type");
        }
    }

    public static AnimatorSet getMoveAnimator(View parentView, View viewToAnimate, View toView,
                                              int paddingLeft, int paddingTop, long duration, long startDelay) {
        final Rect parentViewRect = new Rect();
        parentView.getGlobalVisibleRect(parentViewRect);

        final Rect toViewRect = new Rect();
        toView.getGlobalVisibleRect(toViewRect);


        final Rect viewToAnimateRect = new Rect();
        viewToAnimate.getGlobalVisibleRect(viewToAnimateRect);

        ObjectAnimator translateAnimatorX = ObjectAnimator.ofFloat(viewToAnimate, "translationX", toViewRect.left - viewToAnimateRect.left - paddingLeft);
        ObjectAnimator translateAnimatorY = ObjectAnimator.ofFloat(viewToAnimate, "translationY", toViewRect.top - viewToAnimateRect.top - paddingTop);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new DecelerateInterpolator(1f));
        animatorSet.setDuration(duration);
        animatorSet.setStartDelay(startDelay);


        animatorSet.playTogether(translateAnimatorX, translateAnimatorY);

        return animatorSet;
    }

    public static AnimatorSet getMoveScalingAnimator(View parentView, View viewToAnimate, View toView,
                                                     int paddingLeft, int paddingTop, long duration, long startDelay) {
        AnimatorSet animatorSet = getMoveAnimator(parentView, viewToAnimate, toView, viewToAnimate.getWidth() / 4 - paddingLeft, viewToAnimate.getHeight() / 4 - paddingTop, duration, startDelay);

        ObjectAnimator scaleX = ObjectAnimator.ofFloat(viewToAnimate, "scaleX", 0.5f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(viewToAnimate, "scaleY", 0.5f);

        animatorSet.playTogether(scaleX, scaleY);
        return animatorSet;
    }
}
