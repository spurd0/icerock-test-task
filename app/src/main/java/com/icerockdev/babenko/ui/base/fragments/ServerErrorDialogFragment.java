package com.icerockdev.babenko.ui.base.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.icerockdev.babenko.R;

/**
 * Created by Roman Babenko on 30/04/17.
 */

public class ServerErrorDialogFragment extends DialogFragment {
    public static final String DIALOG_MESSAGE_KEY = ServerErrorDialogFragment.class.getName()
            + ".DIALOG_MESSAGE_KEY";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle arguments = getArguments();
        String message = arguments.getString(DIALOG_MESSAGE_KEY);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.request_data_fields_error))
                .setMessage(message);
        builder.setPositiveButton(android.R.string.ok, null);
        return builder.create();
    }
}
