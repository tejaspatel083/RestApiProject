package com.example.restapiproject.utils;

import com.example.restapiproject.Models.PersonList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("users")
    Call<PersonList> getListByYear(@Query("sort_by") String year, @Query("page") String page);

    //movie_details.json
}
