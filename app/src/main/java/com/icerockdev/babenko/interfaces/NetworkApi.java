package com.icerockdev.babenko.interfaces;

import com.icerockdev.babenko.model.DataField;
import com.icerockdev.babenko.model.Images;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Roman Babenko on 30/04/17.
 */

public interface NetworkApi {
    @GET
    Observable<DataField[]> requestDataFields(@Url String url);

    @GET
    Observable<Images> requestImages(@Url String url);
}
