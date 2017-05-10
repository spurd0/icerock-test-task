package com.icerockdev.babenko.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.interfaces.HomeView;
import com.icerockdev.babenko.managers.DataFieldsManager;
import com.icerockdev.babenko.model.DataField;
import com.icerockdev.babenko.model.DataFieldResponse;
import com.icerockdev.babenko.utils.UtilsHelper;

import static com.icerockdev.babenko.managers.DataFieldsManager.SERVER_ERROR_DIALOG_MESSAGE_KEY;


/**
 * Created by Roman Babenko on 06/05/17.
 */

public class HomePresenter extends BasePresenter<HomeView> {

    private DataFieldsManager mManager = IceRockApplication.getInstance().getDataFieldsManager();

    public void requestDataClicked(String url) {
//        if (!Patterns.WEB_URL.matcher(url).matches()) {
//            getView().showUrlError(IceRockApplication.getInstance().getString(R.string.url_error));
//            return;
//        }
        if (BuildConfig.DEBUG)
            url = "http://www.mocky.io/v2/58fa10ce110000b81ad2106c";
        getView().showProgressDialog();
        mManager.requestDataFields(url, new DataFieldsManager.DataFieldsCallback() {
            @Override
            public void failedResponse(String error) {
                if (getView() != null) {
                    getView().dismissProgressDialog();
                    getView().showErrorDialog(error);
                } else UtilsHelper.saveStringToSharedPreferences(IceRockApplication.getInstance(),
                        SERVER_ERROR_DIALOG_MESSAGE_KEY, error);
            }

            @Override
            public void successResponse(DataFieldResponse[] response) {
                boolean emptyList = response.length == 0;
                if (getView() != null) {
                    getView().dismissProgressDialog();
                    if (!emptyList)
                        getView().gotDataFields(prepareDataFields(response));
                    else getView().showErrorDialog(IceRockApplication.getInstance()
                            .getString(R.string.request_data_fields_error_list_empty));
                } else if (emptyList)
                    UtilsHelper.saveStringToSharedPreferences(IceRockApplication.getInstance(),
                            SERVER_ERROR_DIALOG_MESSAGE_KEY, IceRockApplication.getInstance()
                                    .getString(R.string.request_data_fields_error_list_empty));
            }
        });
    }

    private DataField[] prepareDataFields(DataFieldResponse[] data) {
        DataField[] convertedData = new DataField[data.length];
        for (int i = 0; i < data.length; i++)
            convertedData[i] = new DataField(data[i]);
        return convertedData;
    }

    public void checkForErrors(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String dialogErrorMessage = prefs.getString(SERVER_ERROR_DIALOG_MESSAGE_KEY, "");
        if (!dialogErrorMessage.isEmpty())
            if (getView() != null)
                getView().showErrorDialog(dialogErrorMessage);
    }

}
