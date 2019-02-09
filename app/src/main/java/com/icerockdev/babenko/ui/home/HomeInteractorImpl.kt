package com.icerockdev.babenko.ui.home

import android.util.Patterns

import com.icerockdev.babenko.model.entities.DataField
import com.icerockdev.babenko.model.errors.IncorrectEmailException
import com.icerockdev.babenko.repo.DataFieldsRepository

import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by Roman Babenko on 14/05/17.
 */

class HomeInteractorImpl(private val dataFieldsRepository: DataFieldsRepository) : HomeInteractor {
    override fun requestDataFields(url: String): Single<Array<DataField>> {
        return Completable.fromAction {
            if (!Patterns.WEB_URL.matcher(url).matches()) {
                throw IncorrectEmailException()
            }
        }.andThen(dataFieldsRepository.requestDataFields(url))
    }

    companion object {
        private const val TAG = "HomeInteractorImpl"
    }
}
