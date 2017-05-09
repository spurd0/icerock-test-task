package com.icerockdev.babenko.managers;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.util.SparseArrayCompat;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.EditText;

import com.icerockdev.babenko.BuildConfig;
import com.icerockdev.babenko.IceRockApplication;
import com.icerockdev.babenko.R;
import com.icerockdev.babenko.model.DataField;
import com.icerockdev.babenko.model.DataFieldResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.UnknownFormatConversionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.icerockdev.babenko.data.ApplicationConstants.EMAIL;
import static com.icerockdev.babenko.data.ApplicationConstants.NUMBER;
import static com.icerockdev.babenko.data.ApplicationConstants.PHONE;
import static com.icerockdev.babenko.data.ApplicationConstants.TEXT;
import static com.icerockdev.babenko.data.ApplicationConstants.URL;

/**
 * Created by Roman Babenko on 30/04/17.
 */

public class DataFieldsManager {
    private static final String TAG = "DataFieldsManager";
    public static final String SERVER_ERROR_DIALOG_MESSAGE_KEY = "com.icerockdev.babenko.managers.DataFieldsManager.SERVER_ERROR_DIALOG_MESSAGE_KEY";

    public void requestDataFields(String Url, final DataFieldsCallback callback) {
        if (BuildConfig.DEBUG)
            Url = "http://www.mocky.io/v2/58fa10ce110000b81ad2106c"; // TODO: 30/04/17 move to unit test

        final Call<DataFieldResponse[]> data = IceRockApplication.getInstance().getRetrofitManager()
                .getService().requestDataFields(Url);
        data.enqueue(new Callback<DataFieldResponse[]>() {
            @Override
            public void onResponse(Call<DataFieldResponse[]> call, Response<DataFieldResponse[]> response) {
                if (response.body() == null) {
                    callback.failedResponse(IceRockApplication.getInstance()
                            .getString(R.string.request_data_fields_error_null));
                } else callback.successResponse(response.body());
            }

            @Override
            public void onFailure(Call<DataFieldResponse[]> call, Throwable t) {
                callback.failedResponse(t.getLocalizedMessage());
                if (BuildConfig.DEBUG)
                    Log.e(TAG, "Request error " + t.getLocalizedMessage());
            }
        });
    }

    public interface DataFieldsCallback {
        void failedResponse(String error);

        void successResponse(DataFieldResponse[] response);
    }

    public void checkFields(SparseArrayCompat<EditText> fieldValues, ArrayList<DataField> dataFields,
                            DataFieldsCheckerCallback callback) {
        List<Integer> errorList = new ArrayList<Integer>();
        for (int i = 0; i < fieldValues.size(); i++) {
            int key = fieldValues.keyAt(i);
            EditText fieldValue = fieldValues.get(key);
            for (DataField dataField : dataFields)
                if (dataField.getId() == key) {
                    if (!isFieldDataCorrect(fieldValue.getText().toString(), dataField.getType()))
                        errorList.add(key);
                }
        }
        if (errorList.isEmpty())
            callback.successResponse();
        else callback.failedResponse(errorList);
    }

    private boolean isFieldDataCorrect(String data, String type) {
        if (data.isEmpty())
            return false;
        switch (type) {
            case TEXT:
                int length = data.length();
                return (length > 10 && length < 30);
            case EMAIL:
                return android.util.Patterns.EMAIL_ADDRESS.matcher(data).matches();
            case PHONE:
                Pattern phonePattern = Pattern.compile("^[+7]{2}\\d{10}$");
                return phonePattern.matcher(data).matches();
            case NUMBER:
                Pattern numberPattern = Pattern.compile("^\\d{1,5}$");
                return numberPattern.matcher(data).matches();
            case URL:
                return URLUtil.isValidUrl(data);
            default:
                throw new UnknownFormatConversionException("Unknown type");
        }
    }

    public interface DataFieldsCheckerCallback {
        void successResponse();

        void failedResponse(List<Integer> errorList);
    }
}
