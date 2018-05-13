package com.icerockdev.babenko.model.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Roman Babenko on 30/04/17.
 */
@Parcelize
data class DataField(var id: Int,
                     var type: String,
                     var placeholder: String,
                     var value: String) : Parcelable