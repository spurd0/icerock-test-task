package com.icerockdev.babenko.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Roman Babenko on 30/04/17.
 */

public class DataField implements Parcelable {
    private int mId;
    private String mType;
    private String mPlaceholder;
    private String mDefaultValue;
    private String mValue;

    protected DataField(Parcel in) {
        mId = in.readInt();
        mType = in.readString();
        mPlaceholder = in.readString();
        mDefaultValue = in.readString();
        mValue = in.readString();
    }

    public DataField(DataFieldResponse data) {
        this.mId = data.getId();
        this.mType = data.getType();
        this.mPlaceholder = data.getPlaceholder();
        this.mDefaultValue = data.getDefaultValue();
        mValue = data.getDefaultValue();
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
        return mId;
    }

    public String getType() {
        return mType;
    }

    public String getPlaceholder() {
        return mPlaceholder;
    }

    public String getDefaultValue() {
        return mDefaultValue;
    }

    public void setValue(String value) {
        this.mValue = value;
    }

    public String getValue() {
        return mValue;
    }

    @Override
    public String toString() {
        return "Id is " + mId + " type is " + mType + " placeholder is " + mPlaceholder +
                " defaultValue is " + mDefaultValue + " current is " + mValue;
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
        dest.writeString(mDefaultValue);
        dest.writeString(mValue);
    }
}
