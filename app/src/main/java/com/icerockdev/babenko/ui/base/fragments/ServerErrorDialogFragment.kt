package com.icerockdev.babenko.ui.base.fragments

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog

import com.icerockdev.babenko.R

/**
 * Created by Roman Babenko on 30/04/17.
 */
class ServerErrorDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val arguments = arguments
        var message: String? = context!!.getString(R.string.error_other)
        if (arguments != null) {
            message = arguments.getString(DIALOG_MESSAGE_KEY)
        }
        val builder = AlertDialog.Builder(context!!)
        builder.setTitle(getString(R.string.error_title))
                .setMessage(message)
        builder.setPositiveButton(android.R.string.ok, null)
        return builder.create()
    }

    companion object {
        private const val DIALOG_MESSAGE_KEY = "ServerErrorDialogFragment.DIALOG_MESSAGE_KEY"

        fun getDialog(error: String): ServerErrorDialogFragment {
            val arguments = Bundle()
            arguments.putString(DIALOG_MESSAGE_KEY, error)
            val serverErrorDialogFragment = ServerErrorDialogFragment()
            serverErrorDialogFragment.arguments = arguments
            return serverErrorDialogFragment
        }
    }
}
