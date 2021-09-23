package com.example.api.interfaces;
import com.example.api.models.UserClass;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserInterface {

    @POST("User/addItens")

    Call<UserClass> createPost(@Body UserClass dataModal);

}
