package com.icerockdev.babenko.interfaces;

import com.icerockdev.babenko.model.DataFieldResponse;
import com.icerockdev.babenko.model.ImageResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Roman Babenko on 30/04/17.
 */

public interface NetworkApi {
    @GET
    Call<DataFieldResponse[]> requestDataFields(@Url String url);

    @GET
    Call<ImageResponse[]> requestImages(@Url String url);
}
