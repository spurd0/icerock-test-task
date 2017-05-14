package com.icerockdev.babenko.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Patterns;

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
    private Context mContext;

    public HomePresenter(Context context) {
        mContext = context;
    }

    @Override
    public void attachView(HomeView homeView) {
        super.attachView(homeView);
        checkForErrors(mContext);
    }

    public void requestDataClicked(String url) {
        if (!Patterns.WEB_URL.matcher(url).matches()) {
            getView().showUrlError(IceRockApplication.getInstance().getString(R.string.url_error));
            return;
        }
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
            public void successResponse(DataField[] response) {
                boolean emptyList = response.length == 0;
                if (getView() != null) {
                    getView().dismissProgressDialog();
                    if (!emptyList)
                        getView().gotDataFields(response);
                    else getView().showErrorDialog(IceRockApplication.getInstance()
                            .getString(R.string.request_data_fields_error_list_empty));
                } else if (emptyList)
                    UtilsHelper.saveStringToSharedPreferences(IceRockApplication.getInstance(),
                            SERVER_ERROR_DIALOG_MESSAGE_KEY, IceRockApplication.getInstance()
                                    .getString(R.string.request_data_fields_error_list_empty));
            }
        });
    }


    public void checkForErrors(Context context) {
        String dialogErrorMessage = UtilsHelper.getStringFromSharedPreferences(context,
                SERVER_ERROR_DIALOG_MESSAGE_KEY);
        if (!dialogErrorMessage.isEmpty())
            if (getView() != null)
                getView().showErrorDialog(dialogErrorMessage);
    }

}
