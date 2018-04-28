package com.icerockdev.babenko.ui.data_fields;

import android.support.v4.util.SparseArrayCompat;
import android.widget.EditText;

import com.icerockdev.babenko.model.entities.DataField;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by Roman Babenko on 30/04/17.
 */

public interface DataFieldsInteractor {

    Single<List<Integer>> checkFields(SparseArrayCompat<EditText> fieldValues);

    Single<List<DataField>> requestDataFields();

}
