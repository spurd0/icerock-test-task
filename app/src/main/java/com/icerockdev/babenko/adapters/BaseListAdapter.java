package com.icerockdev.babenko.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Roman Babenko on 06/05/17.
 */

public abstract class BaseListAdapter<Object> {
    protected Context mContext;
    private List<Object> mObjects;
    private ViewGroup mViewGroup;
    private ViewGroup mParent;

    public BaseListAdapter(@NonNull Context context, @NonNull List<Object> objects, ViewGroup parent) {
        mContext = context;
        mObjects = objects;
        mParent = parent; // sorry i don`t know how original adapter get it :(
    }

    public void attachAdapter(ViewGroup viewGroup) {
        mViewGroup = viewGroup;
        addElements();
    }

    private void addElements() {
        for (int i = 0; i < mObjects.size(); i++) {
            mViewGroup.addView(getView(i, mParent));
        }
    }

    protected Object getItem(int position) {
        return mObjects.get(position);
    }

    protected abstract View getView(int position, ViewGroup parent);

}
