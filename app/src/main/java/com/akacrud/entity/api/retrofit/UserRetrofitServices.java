package com.akacrud.entity.api.retrofit;

import com.akacrud.entity.model.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by luisvespa on 12/13/17.
 */

public interface UserRetrofitServices {

    @GET("/api/user/getall")
    Single<List<User>> getUsers();

    @GET("/api/user/get/{id}")
    Single<User> getById(@Path("id") int id);

    @Headers("Content-Type: application/json")
    @POST("/api/user/create")
    Completable create(@Body User body);

    @Headers("Content-Type: application/json")
    @POST("/api/user/update")
    Single<User> update(@Body User body);

    @GET("/api/user/remove/{id}")
    Completable remove(@Path("id") int id);

}
