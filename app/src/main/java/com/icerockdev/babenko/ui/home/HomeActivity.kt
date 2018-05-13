package com.icerockdev.babenko.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.icerockdev.babenko.BuildConfig
import com.icerockdev.babenko.IceRockApplication
import com.icerockdev.babenko.R
import com.icerockdev.babenko.model.entities.DataField
import com.icerockdev.babenko.repo.DataFieldsRepository
import com.icerockdev.babenko.ui.base.activities.BaseProgressActivity
import com.icerockdev.babenko.ui.base.fragments.ServerErrorDialogFragment
import com.icerockdev.babenko.utils.ErrorCleaningWatcher
import com.icerockdev.babenko.utils.UtilsHelper
import timber.log.Timber
import javax.inject.Inject

class HomeActivity : BaseProgressActivity(), HomeView {
    @InjectPresenter
    lateinit var mPresenter: HomePresenter

    @Inject
    lateinit var dataFieldsRepository: DataFieldsRepository

    @BindView(R.id.fields_request_url_edit_text)
    lateinit var fieldsRequestUrlEditText: EditText
    @BindView(R.id.fields_request_url_input)
    lateinit var fieldsRequestUrlInput: TextInputLayout

    private var mDataFieldsUrlTextWatcher: TextWatcher? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        ButterKnife.bind(this)

        initViews()
    }

    @ProvidePresenter
    internal fun provideHomePresenter(): HomePresenter {
        IceRockApplication.appComponent.inject(this)
        return HomePresenter(HomeInteractorImpl(dataFieldsRepository))
    }

    private fun initViews() {
        fieldsRequestUrlEditText.setText(getString(R.string.url_start))
        fieldsRequestUrlEditText.setSelection(fieldsRequestUrlEditText.text.length)
        if (BuildConfig.DEBUG) {
            fieldsRequestUrlEditText.setText(FIELDS_LINK)
        }
        initWatchers()
    }

    private fun initWatchers() {
        mDataFieldsUrlTextWatcher = ErrorCleaningWatcher(fieldsRequestUrlInput)
        fieldsRequestUrlEditText.addTextChangedListener(mDataFieldsUrlTextWatcher)
    }

    override fun onDestroy() {
        releaseWatchers()
        super.onDestroy()
    }

    private fun releaseWatchers() {
        fieldsRequestUrlEditText.removeTextChangedListener(mDataFieldsUrlTextWatcher)
        mDataFieldsUrlTextWatcher = null
    }

    fun requestDataFieldsButtonClicked(v: View) {
        if (UtilsHelper.isNetworkAvailable(this)) {
            mPresenter.requestDataClicked(fieldsRequestUrlEditText.text.toString())
        } else {
            showErrorDialog(getString(R.string.error_no_network))
        }
    }

    private fun showErrorDialog(error: String) {
        val serverErrorDialogFragment = ServerErrorDialogFragment.getDialog(error)
        serverErrorDialogFragment.show(supportFragmentManager, SERVER_ERROR_DIALOG_TAG)
    }

    override fun gotDataFields(data: Array<DataField>) {
        Timber.tag(TAG).d("Data field count is:%s", data.size)
        navigator.navigateToDataFieldsActivity(this, data)
    }

    override fun showTimeoutError() {
        showErrorDialog(getString(R.string.error_timeout))
    }

    override fun showErrorDialog() {
        showErrorDialog(getString(R.string.error_other))
    }

    override fun showUrlError() {
        fieldsRequestUrlEditText.error = getString(R.string.url_error)
    }

    companion object {
        private const val FIELDS_LINK = "http://www.mocky.io/v2/58fa10ce110000b81ad2106c"
        private const val SERVER_ERROR_DIALOG_TAG = "HomeActivity.SERVER_ERROR_DIALOG_TAG"
        private const val TAG = "HomeActivity"

        fun getLaunchingIntent(context: Context): Intent {
            return Intent(context, HomeActivity::class.java)
        }
    }
}
