package com.example.api.interfaces;

import com.example.api.models.MuseuClass;
import com.example.api.models.UserClass;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface MuseumInterface {

    @POST("Museum/addItens")
    Call<MuseuClass> createPost(@Body MuseuClass dataModal);

}
