package com.icerockdev.babenko.ui.base.fragments

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.view.ContextThemeWrapper
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import com.icerockdev.babenko.R

/**
 * Created by Roman Babenko on 01/05/17.
 */

class ProgressDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogContext = ContextThemeWrapper(context, R.style.AppTheme)
        val dialog = Dialog(dialogContext)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.show()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        dialog.setContentView(R.layout.progress_dialog)
        val progressBar = dialog.findViewById<View>(R.id.progress_bar) as ProgressBar
        progressBar.indeterminateDrawable.setColorFilter(
            ContextCompat.getColor(
                context!!,
                R.color.progressDialogColor
            ), android.graphics.PorterDuff.Mode.MULTIPLY
        )
        isCancelable = false
        return dialog
    }
}
