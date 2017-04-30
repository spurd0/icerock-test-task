package com.icerockdev.babenko.utils;


import android.app.Activity;
import android.app.Dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;

import android.view.ContextThemeWrapper;
import android.view.Window;
import android.widget.ProgressBar;

import com.icerockdev.babenko.R;

/**
 * Created by Roman Babenko on 30/04/17.
 */

public class UtilsHelper {
    public static Dialog createProgressDialog(Activity activity) {
        final Context dialogContext = new ContextThemeWrapper(activity, R.style.AppTheme);
        Dialog dialog = new Dialog(dialogContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.show();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.progress_dialog);
        ProgressBar progressBar = (ProgressBar) dialog.findViewById(R.id.progress_bar);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,
                R.color.progress_dialog_color), android.graphics.PorterDuff.Mode.MULTIPLY);
        dialog.setCancelable(false);
        return dialog;
    }
}
