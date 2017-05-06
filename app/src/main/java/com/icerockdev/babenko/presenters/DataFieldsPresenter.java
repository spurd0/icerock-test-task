package com.icerockdev.babenko.presenters;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.util.Log;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.interfaces.DataFieldsView;
import com.icerockdev.babenko.model.DataField;

import java.util.ArrayList;

import static com.icerockdev.babenko.activities.DataFieldsActivity.DATA_FIELDS_KEY;

/**
 * Created by Roman Babenko on 06/05/17.
 */

public class DataFieldsPresenter extends BasePresenter<DataFieldsView> {

    public void requestFieldsData(Activity activity) {
        Parcelable[] data = activity.getIntent().getParcelableArrayExtra(DATA_FIELDS_KEY);
        ArrayList<DataField> dataFieldsList = new ArrayList<>();
        for (Parcelable aData : data) {
            dataFieldsList.add((DataField) aData);
        }
        getView().gotFieldsData( dataFieldsList);
    }

}
