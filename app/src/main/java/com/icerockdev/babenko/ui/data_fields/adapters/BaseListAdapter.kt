package com.icerockdev.babenko.ui.data_fields.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup

/**
 * Created by Roman Babenko on 06/05/17.
 */

abstract class BaseListAdapter<Object>(protected var mContext: Context, private val mObjects: List<Object>) {
    lateinit var mParent: ViewGroup
    fun attachAdapter(viewGroup: ViewGroup) {
        mParent = viewGroup
        addElements()
    }

    private fun addElements() {
        for (i in mObjects.indices) {
            mParent.addView(getView(i, mParent))
        }
    }

    protected fun getItem(position: Int): Object {
        return mObjects[position]
    }

    protected abstract fun getView(position: Int, parent: ViewGroup): View

}
