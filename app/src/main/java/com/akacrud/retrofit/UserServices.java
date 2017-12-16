package com.akacrud.retrofit;

import com.akacrud.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by luisvespa on 12/13/17.
 */

public interface UserServices {

    @GET("/api/user/getall")
    Call<List<User>> getAll();

    @GET("/api/user/get/{id}")
    Call<User> getById(@Path("id") int id);

    @Headers("Content-Type: application/json")
    @POST("/api/user/create")
    Call<User> create(@Body User body);

    @Headers("Content-Type: application/json")
    @POST("/api/user/update")
    Call<User> update(@Body User body);

    @GET("/api/user/remove/{id}")
    Call<Void> remove(@Path("id") int id);

}
