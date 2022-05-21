package com.example.learning.utils;



import com.example.learning.model.User;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;


public interface RetrofitInterface {

    @POST("/API/login")
    Call<User> login(@Body HashMap<String, Object> map);

    @PUT("/API/save-score")
    Call<User> saveScore(@Body HashMap<String, Object> map);
}
