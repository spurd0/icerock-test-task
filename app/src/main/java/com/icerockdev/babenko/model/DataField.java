package com.icerockdev.babenko.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Roman Babenko on 30/04/17.
 */

public class DataField implements Parcelable {
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
    private int mId;
    private String mType;
    private String mPlaceholder;
    private String mValue;

    protected DataField(Parcel in) {
        mId = in.readInt();
        mType = in.readString();
        mPlaceholder = in.readString();
        mValue = in.readString();
    }

    public DataField(int id,
                     String type,
                     String placeholder,
                     String defaultValue) {
        mId = id;
        mType = type;
        mPlaceholder = placeholder;
        mValue = defaultValue;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getPlaceholder() {
        return mPlaceholder;
    }

    public void setPlaceholder(String placeholder) {
        mPlaceholder = placeholder;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }

    public void setDefaultValue(String default_value) {
        mValue = default_value;
    }

    @Override
    public String toString() {
        return "Id is " + mId + " type is " + mType + " placeholder is " + mPlaceholder +
                " current is " + mValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mType);
        dest.writeString(mPlaceholder);
        dest.writeString(mValue);
    }
}
