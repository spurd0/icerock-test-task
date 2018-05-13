package com.icerockdev.babenko.managers.impl

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

import com.icerockdev.babenko.managers.SharedPreferencesManager

/**
 * Created by Roman Babenko on 10/05/17.
 */

class SharedPreferencesManagerImpl(context: Context) : SharedPreferencesManager {
    private val mManager: SharedPreferences

    override val errorCode: Int
        get() = mManager.getInt(ERROR_CODE_KEY, 0)

    override var isUserLogged: Boolean
        get() = mManager.getBoolean(IS_USER_LOGGED_KEY, false)
        set(logged) = mManager.edit().putBoolean(IS_USER_LOGGED_KEY, logged).apply()

    init {
        mManager = PreferenceManager.getDefaultSharedPreferences(context)
    }

    override fun saveErrorCode(code: Int) {
        mManager.edit().putInt(ERROR_CODE_KEY, code).apply()
    }

    companion object {
        private val ERROR_CODE_KEY = "SharedPreferencesManagerImpl.ERROR_CODE_KEY"
        private val IS_USER_LOGGED_KEY = "SharedPreferencesManagerImpl.IS_USER_LOGGED_KEY"
    }


}
