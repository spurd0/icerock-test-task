package com.icerockdev.babenko.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.icerockdev.babenko.model.DataField;

/**
 * Created by Roman Babenko on 01/05/17.
 */

public class RequestStateMessage implements Parcelable {
    private boolean mFinished = true;
    private boolean mSuccess = false;
    private String mErrorMessage;
    private DataField[] mDataFields;

    public RequestStateMessage() {
    }


    protected RequestStateMessage(Parcel in) {
        mFinished = in.readByte() != 0;
        mSuccess = in.readByte() != 0;
        mErrorMessage = in.readString();
        mDataFields = in.createTypedArray(DataField.CREATOR);
    }

    public static final Creator<RequestStateMessage> CREATOR = new Creator<RequestStateMessage>() {
        @Override
        public RequestStateMessage createFromParcel(Parcel in) {
            return new RequestStateMessage(in);
        }

        @Override
        public RequestStateMessage[] newArray(int size) {
            return new RequestStateMessage[size];
        }
    };

    public void setErrorMessage(String mErrorMessage) {
        this.mErrorMessage = mErrorMessage;
    }

    public void setFinished(boolean mFinished) {
        this.mFinished = mFinished;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public boolean isFinished() {
        return mFinished;
    }

    public DataField[] getDataFields() {
        return mDataFields;
    }

    public void setDataFields(DataField[] dataFields) {
        this.mDataFields = dataFields;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void setSuccess(boolean mSuccess) {
        this.mSuccess = mSuccess;
    }

    public boolean isSuccess() {
        return mSuccess;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (mFinished ? 1 : 0));
        dest.writeByte((byte) (mSuccess ? 1 : 0));
        dest.writeString(mErrorMessage);
        dest.writeTypedArray(mDataFields, 0);
    }
}
