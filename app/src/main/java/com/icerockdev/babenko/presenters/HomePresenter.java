package com.icerockdev.babenko.presenters;

import android.webkit.URLUtil;

import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.interfaces.HomeView;
import com.icerockdev.babenko.managers.DataFieldsManager;
import com.icerockdev.babenko.model.DataField;
import com.icerockdev.babenko.utils.UtilsHelper;

import static com.icerockdev.babenko.managers.DataFieldsManager.SERVER_ERROR_DIALOG_MESSAGE_KEY;


/**
 * Created by Roman Babenko on 06/05/17.
 */

public class HomePresenter extends BasePresenter<HomeView> {

    private DataFieldsManager mManager = IceRockApplication.getInstance().getDataFieldsManager();

    public void requestDataClicked() {
        String url = getView().getUrl();
        if (!URLUtil.isValidUrl(url)) {
            getView().showUrlError(IceRockApplication.getInstance().getString(R.string.url_error));
            return;
        }
        getView().showProgressDialog();
        mManager.requestDataFields(url, new DataFieldsManager.DataFieldsCallback() {
            @Override
            public void failedResponse(String error) {
                UtilsHelper.saveStringToSharedPreferences(IceRockApplication.getInstance(),
                        SERVER_ERROR_DIALOG_MESSAGE_KEY, error);
                if (getView() != null) {
                    getView().dismissProgressDialog();
                    getView().showErrorDialog(error);
                }
            }

            @Override
            public void successResponse(DataField[] response) {
                if (getView() != null) {
                    getView().dismissProgressDialog();
                    if (response.length != 0)
                        getView().gotDataFields(response);
                    else getView().showErrorDialog(IceRockApplication.getInstance()
                            .getString(R.string.request_data_fields_error_list_empty));
                }
            }
        });
    }

}
