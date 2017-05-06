package com.icerockdev.babenko.presenters;

import android.webkit.URLUtil;

import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.fragments.ProgressDialogFragment;
import com.icerockdev.babenko.interfaces.HomeView;
import com.icerockdev.babenko.managers.DataFieldsManager;

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
        mManager.requestDataFields(url);
    }
}
