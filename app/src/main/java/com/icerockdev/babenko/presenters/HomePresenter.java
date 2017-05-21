package com.icerockdev.babenko.presenters;

import android.util.Patterns;

import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.interfaces.HomeView;
import com.icerockdev.babenko.managers.impl.HomeManagerImpl;
import com.icerockdev.babenko.managers.interfaces.HomeManager;
import com.icerockdev.babenko.managers.interfaces.SharedPreferencesManager;
import com.icerockdev.babenko.model.DataField;


/**
 * Created by Roman Babenko on 06/05/17.
 */

public class HomePresenter extends BasePresenter<HomeView> {
    private HomeManager mManager;
    private SharedPreferencesManager mSharedPreferencesManager;

    public HomePresenter(HomeManager manager, SharedPreferencesManager sharedPreferencesManager) {
        mManager = manager;
        mSharedPreferencesManager = sharedPreferencesManager;
    }

    @Override
    public void attachView(HomeView homeView) {
        super.attachView(homeView);
        checkForErrors();
    }

    public void requestDataClicked(String url) {
        if (!Patterns.WEB_URL.matcher(url).matches()) {
            if (getView() != null)
                getView().showUrlError(IceRockApplication.getInstance().getString(R.string.url_error));
            return;
        }
        if (getView() != null)
            getView().showProgressDialog();
        mManager.requestDataFields(url, new HomeManagerImpl.DataFieldsCallback() {
            @Override
            public void failedResponse(String error) {
                if (getView() != null) {
                    getView().dismissProgressDialog();
                    getView().showErrorDialog(error);
                } else mSharedPreferencesManager.saveErrorMessage(error);
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
                    mSharedPreferencesManager.saveErrorMessage(IceRockApplication.getInstance()
                            .getString(R.string.request_data_fields_error_list_empty));
            }
        });
    }


    private void checkForErrors() {
        String dialogErrorMessage = mSharedPreferencesManager.getErrorMessage();
        if (!dialogErrorMessage.isEmpty())
            if (getView() != null)
                getView().showErrorDialog(dialogErrorMessage);
    }

}
