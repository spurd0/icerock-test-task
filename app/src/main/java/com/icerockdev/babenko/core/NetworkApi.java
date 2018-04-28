package com.icerockdev.babenko.core;

import com.icerockdev.babenko.model.entities.DataField;
import com.icerockdev.babenko.model.entities.ImageItem;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Roman Babenko on 30/04/17.
 */

public interface NetworkApi {
    @GET
    Single<DataField[]> requestDataFields(@Url String url);

    @GET
    Single<ImageItem[]> requestImages(@Url String url);
}
