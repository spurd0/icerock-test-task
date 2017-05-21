package com.icerockdev.babenko.utils;

import android.content.Context;
import android.content.res.Resources;
import android.text.InputType;
import android.util.TypedValue;

import com.icerockdev.babenko.R;
import com.icerockdev.babenko.model.DataField;
import com.icerockdev.babenko.model.DataFieldResponse;
import com.icerockdev.babenko.model.ImageItem;
import com.icerockdev.babenko.model.ImageResponse;

import java.util.ArrayList;

import static com.icerockdev.babenko.data.ApplicationConstants.EMAIL;
import static com.icerockdev.babenko.data.ApplicationConstants.NUMBER;
import static com.icerockdev.babenko.data.ApplicationConstants.PHONE;
import static com.icerockdev.babenko.data.ApplicationConstants.TEXT;
import static com.icerockdev.babenko.data.ApplicationConstants.URL;

/**
 * Created by Roman Babenko on 03/05/17.
 */

public class UtilsHelper {
    public static int convertDpToPx(Context context, float dpValue) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dpValue,
                r.getDisplayMetrics()
        );
    }

    public static int getInputType(String type) {
        switch (type) {
            case TEXT:
                return InputType.TYPE_CLASS_TEXT;
            case EMAIL:
                return InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS;
            case PHONE:
                return InputType.TYPE_CLASS_PHONE;
            case NUMBER:
                return InputType.TYPE_CLASS_NUMBER;
            case URL:
                return InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS;
            default:
                throw new IllegalArgumentException("Unknown type");
        }
    }

    public static String getInputHint(String type, Context context) {
        switch (type) {
            case TEXT:
                return context.getString(R.string.data_field_text_hint);
            case EMAIL:
                return context.getString(R.string.data_field_email_hint);
            case PHONE:
                return context.getString(R.string.data_field_phone_hint);
            case NUMBER:
                return context.getString(R.string.data_field_number_hint);
            case URL:
                return context.getString(R.string.data_field_url_hint);
            default:
                throw new IllegalArgumentException("Unknown type");
        }
    }

    public static DataField[] convertDataFields(DataFieldResponse[] data) {
        DataField[] convertedData = new DataField[data.length];
        for (int i = 0; i < data.length; i++)
            convertedData[i] = new DataField(data[i].getId(), data[i].getType(),
                    data[i].getPlaceholder(),
                    data[i].getDefaultValue());
        return convertedData;
    }

    public static ArrayList<ImageItem> convertImagesList(ImageResponse[] images) {
        ArrayList<ImageItem> result = new ArrayList<>();
        for (ImageResponse imageResponse : images) {
            result.add(new ImageItem(imageResponse.getAlbumId(), imageResponse.getId(),
                    imageResponse.getTitle(), imageResponse.getUrl(), imageResponse.getThumbnailUrl()));
        }
        return result;
    }
}
