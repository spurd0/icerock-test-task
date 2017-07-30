package com.icerockdev.babenko.ui.home;

import android.util.Patterns;

import com.arellomobile.mvp.InjectViewState;
import com.icerockdev.babenko.managers.impl.HomeManagerImpl;
import com.icerockdev.babenko.managers.interfaces.HomeManager;
import com.icerockdev.babenko.managers.interfaces.SharedPreferencesManager;
import com.icerockdev.babenko.model.DataField;
import com.icerockdev.babenko.ui.BasePresenter;

import static com.icerockdev.babenko.managers.impl.HomeManagerImpl.ERROR_CODE_RESPONSE_NULL;
import static com.icerockdev.babenko.managers.impl.HomeManagerImpl.ERROR_CODE_RESPONSE_OTHER;

/**
 * Created by Roman Babenko on 06/05/17.
 */
@InjectViewState
public class HomePresenter extends BasePresenter<HomeView> {
    public static final int CODE_ERROR_EMPTY_LIST = 1;
    public static final int CODE_ERROR_LIST_NULL_RESPONSE = 2;
    public static final int CODE_ERROR_OTHER = 3;
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
            if (getViewState() != null)
                getViewState().showUrlError();
            return;
        }
        if (getViewState() != null)
            getViewState().showProgressDialog();
        mManager.requestDataFields(url, new HomeManagerImpl.DataFieldsCallback() {
            @Override
            public void failedResponse(int errorCode) {
                int listErrorCode = 0;
                switch (errorCode) {
                    case ERROR_CODE_RESPONSE_NULL:
                        listErrorCode = CODE_ERROR_LIST_NULL_RESPONSE;
                        break;
                    case ERROR_CODE_RESPONSE_OTHER:
                        listErrorCode = CODE_ERROR_OTHER;
                        break;
                    default:
                        listErrorCode = CODE_ERROR_OTHER;
                        break;
                }
                if (getViewState() != null) {
                    getViewState().dismissProgressDialog();
                    getViewState().showErrorDialog(listErrorCode);
                } else mSharedPreferencesManager.saveErrorCode(listErrorCode);
            }

            @Override
            public void successResponse(DataField[] response) {
                boolean emptyList = response.length == 0;
                if (getViewState() != null) {
                    getViewState().dismissProgressDialog();
                    if (!emptyList)
                        getViewState().gotDataFields(response);
                    else getViewState().showErrorDialog(CODE_ERROR_EMPTY_LIST);
                } else if (emptyList)
                    mSharedPreferencesManager.saveErrorCode(CODE_ERROR_EMPTY_LIST);
            }
        });
    }


    private void checkForErrors() {
        int errorCode = mSharedPreferencesManager.getErrorCode();
        if (errorCode == 0)
            return;
        if (getViewState() != null) {
            mSharedPreferencesManager.saveErrorCode(0);
            getViewState().showErrorDialog(errorCode);
        }
    }
}


