package com.icerockdev.babenko.managers

/**
 * Created by Roman Babenko on 10/05/17.
 */

interface SharedPreferencesManager {
    val errorCode: Int

    var isUserLogged: Boolean

    fun saveErrorCode(code: Int)
}
