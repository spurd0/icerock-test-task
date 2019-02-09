package com.icerockdev.babenko.ui.data_fields

import android.support.v4.util.SparseArrayCompat
import android.widget.EditText

import com.icerockdev.babenko.model.entities.DataField

import io.reactivex.Single

/**
 * Created by Roman Babenko on 30/04/17.
 */

interface DataFieldsInteractor {

    fun checkFields(fieldValues: SparseArrayCompat<EditText>): Single<List<Int>>

    fun requestDataFields(): Single<List<DataField>>

}
