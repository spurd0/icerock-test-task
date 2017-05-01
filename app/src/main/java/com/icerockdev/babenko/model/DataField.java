package com.icerockdev.babenko.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Roman Babenko on 30/04/17.
 */

public class DataField implements Parcelable {
    private int id;
    private String type;
    private String placeholder;
    private String default_value;

    protected DataField(Parcel in) {
        id = in.readInt();
        type = in.readString();
        placeholder = in.readString();
        default_value = in.readString();
    }

    public static final Creator<DataField> CREATOR = new Creator<DataField>() {
        @Override
        public DataField createFromParcel(Parcel in) {
            return new DataField(in);
        }

        @Override
        public DataField[] newArray(int size) {
            return new DataField[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public String getDefault_value() {
        return default_value;
    }

    @Override
    public String toString() {
        return "Id is " + id + " type is " + type + " placeholder is " + placeholder +
                " default_value is " + default_value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(type);
        dest.writeString(placeholder);
        dest.writeString(default_value);
    }
}
