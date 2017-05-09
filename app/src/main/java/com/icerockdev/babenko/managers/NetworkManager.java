package com.icerockdev.babenko.managers;

import com.icerockdev.babenko.model.DataFieldResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by Roman Babenko on 30/04/17.
 */

public interface NetworkManager {
    @GET
    Call<DataFieldResponse[]> requestDataFields(@Url String url);
}
