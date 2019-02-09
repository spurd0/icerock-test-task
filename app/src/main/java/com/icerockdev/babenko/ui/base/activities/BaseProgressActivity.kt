package com.icerockdev.babenko.ui.base.activities

import android.os.Bundle

import com.icerockdev.babenko.ui.base.fragments.ProgressDialogFragment
import com.icerockdev.babenko.ui.base.views.ProgressBaseView

/**
 * Created by Roman Babenko on 14/05/17.
 */

abstract class BaseProgressActivity : BaseActivity(), ProgressBaseView {
    private var allowToShowDialog: Boolean = false

    override fun showProgressDialog() {
        if (!allowToShowDialog) {
            return
        }
        var progressDialogFragment: ProgressDialogFragment? =
            supportFragmentManager.findFragmentByTag(mDialogTag) as? ProgressDialogFragment
        if (progressDialogFragment != null) {
            return
        }
        progressDialogFragment = ProgressDialogFragment()
        supportFragmentManager.beginTransaction().add(progressDialogFragment, mDialogTag).commit()
        supportFragmentManager.executePendingTransactions()
    }

    override fun dismissProgressDialog() {
        if (!allowToShowDialog) {
            return
        }
        val progressDialogFragment: ProgressDialogFragment? = supportFragmentManager
            .findFragmentByTag(mDialogTag) as ProgressDialogFragment?
        progressDialogFragment?.dismiss()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        allowToShowDialog = false
        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()
        allowToShowDialog = true
    }

    override fun onDestroy() {
        dismissProgressDialog()
        super.onDestroy()
    }

    companion object {
        private const val mDialogTag = "BaseProgressActivity.PROGRESS_DIALOG_TAG"
    }
}
