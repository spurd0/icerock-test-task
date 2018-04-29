package com.icerockdev.babenko.ui.home;

import android.util.Patterns;

import com.icerockdev.babenko.model.entities.DataField;
import com.icerockdev.babenko.model.errors.IncorrectEmailException;
import com.icerockdev.babenko.repo.DataFieldsRepository;

import io.reactivex.Completable;
import io.reactivex.Single;

/**
 * Created by Roman Babenko on 14/05/17.
 */

public class HomeInteractorImpl implements HomeInteractor {
    private static final String TAG = "HomeInteractorImpl";
    private final DataFieldsRepository dataFieldsRepository;

    public HomeInteractorImpl(DataFieldsRepository dataFieldsRepository) {
        this.dataFieldsRepository = dataFieldsRepository;
    }

    public Single<DataField[]> requestDataFields(String url) {
        return Completable.fromAction(() -> {
            if (!Patterns.WEB_URL.matcher(url).matches()) {
                throw new IncorrectEmailException();
            }
        }).andThen(dataFieldsRepository.requestDataFields(url));
    }

}
