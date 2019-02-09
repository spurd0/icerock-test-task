package com.icerockdev.babenko.ui.data_fields

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.AppCompatButton
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.icerockdev.babenko.BuildConfig
import com.icerockdev.babenko.R
import com.icerockdev.babenko.core.ApplicationConstants.EMAIL
import com.icerockdev.babenko.core.ApplicationConstants.NUMBER
import com.icerockdev.babenko.core.ApplicationConstants.PHONE
import com.icerockdev.babenko.core.ApplicationConstants.TEXT
import com.icerockdev.babenko.core.ApplicationConstants.URL
import com.icerockdev.babenko.model.entities.DataField
import com.icerockdev.babenko.ui.base.activities.BaseActivity
import com.icerockdev.babenko.ui.data_fields.adapters.DataFieldsAdapter

/**
 * Created by Roman Babenko on 01/05/17.
 */

class DataFieldsActivity : BaseActivity(), DataFieldsView {
    @InjectPresenter
    lateinit var mPresenter: DataFieldsPresenter

    @BindView(R.id.validationErrorTv)
    lateinit var validationErrorTv: TextView
    @BindView(R.id.submitFieldsButton)
    lateinit var submitFieldsButton: AppCompatButton

    private var mDataFieldsAdapter: DataFieldsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_fields)

        ButterKnife.bind(this)
    }

    @ProvidePresenter
    internal fun provideDataFieldsPresenter(): DataFieldsPresenter {
        val dataFields = intent.getParcelableArrayExtra(DATA_FIELDS_KEY)
                .filterIsInstance<DataField>()
                .toTypedArray()
        return DataFieldsPresenter(DataFieldsInteractorImpl(dataFields))
    }


    override fun showError() {
        validationErrorTv.visibility = View.VISIBLE
        validationErrorTv.text = getText(R.string.data_field_incorrect_format)
        validationErrorTv.requestFocus()
    }

    override fun showDataFields(dataFields: List<DataField>) {
        if (BuildConfig.DEBUG) {
            fillDataFields(dataFields)
        }

        mDataFieldsAdapter = DataFieldsAdapter(this, dataFields)
        // hope that it won`t be too much fields, moved to recyclerview @see feature/recycler_view_datafields
        mDataFieldsAdapter!!.attachAdapter(findViewById<View>(R.id.dataFieldsEditTextContainer) as LinearLayout)

        submitFieldsButton.isEnabled = true
    }

    private fun fillDataFields(dataFields: List<DataField>) {// TODO: 15/05/17 how to correctly make a testing or skip it?
        for (dataField in dataFields) {
            when (dataField.type) {
                TEXT -> dataField.value = "Very-very-very long text"
                EMAIL -> dataField.value = "foo@java.com"
                PHONE -> dataField.value = "+79991234200"
                NUMBER -> dataField.value = "12345"
                URL -> dataField.value = "ya.ru"
            }
        }
    }

    override fun displayFieldsError(errorList: List<Int>) {
        showError()
        mDataFieldsAdapter!!.updateErrorsViews(errorList)
    }

    override fun fieldsSuccessfullyChecked() {
        navigator.navigateToImagesActivity(this)
        finish()
    }

    override fun hideError() {
        validationErrorTv.visibility = View.GONE
    }

    fun submitFieldsClicked(view: View) {
        hideError()
        mPresenter.submitButtonPressed(mDataFieldsAdapter!!.fieldValues)
    }

    companion object {
        private const val TAG = "DataFieldsActivity"
        private const val DATA_FIELDS_KEY = "DataFieldsActivity.DATA_FIELDS_KEY"

        fun getStartingIntent(context: Context, data: Array<DataField>): Intent {
            val dataFieldsIntent = Intent(context, DataFieldsActivity::class.java)
            dataFieldsIntent.putExtra(DATA_FIELDS_KEY, data)
            return dataFieldsIntent
        }
    }
}
