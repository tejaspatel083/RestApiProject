package com.example.restapiproject.utils;

import com.example.restapiproject.Models.Model;
import com.example.restapiproject.Models.PersonList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("api/users?page=2")
    Call<Model> getAllData();

    //movie_details.json
}
