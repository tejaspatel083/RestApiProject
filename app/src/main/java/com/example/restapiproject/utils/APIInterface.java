package com.example.restapiproject.utils;

import com.example.restapiproject.Models.Model;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("api/users")
    Call<Model> getAllData();

}
