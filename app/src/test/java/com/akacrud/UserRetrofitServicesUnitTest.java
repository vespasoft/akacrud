package com.akacrud;

import com.akacrud.entity.model.User;
import com.akacrud.entity.api.retrofit.ApiUtils;
import com.akacrud.entity.api.retrofit.UserRetrofitServices;

import org.junit.Test;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.junit.Assert.*;


/**
 * Created by luisvespa on 12/16/17.
 * UserRetrofitServices local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class UserRetrofitServicesUnitTest {

    @Test
    public void getAll_isCorrect() throws Exception {
        UserRetrofitServices mAPIUserService = ApiUtils.getAPIUserService();
        mAPIUserService.getAll().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                assertTrue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                assertFalse(t.getMessage(), (t != null) );
            }
        });
    }

    @Test
    public void create_isCorrect() throws Exception {
        UserRetrofitServices mAPIUserService = ApiUtils.getAPIUserService();
        mAPIUserService.create(new User(0, "User Test", "2002-03-21T00:00:00")).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                assertTrue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                assertFalse(t.getMessage(), (t != null) );
            }
        });
    }

    @Test
    public void update_isCorrect() throws Exception {
        UserRetrofitServices mAPIUserService = ApiUtils.getAPIUserService();
        mAPIUserService.update(new User(4905, "User test edited", "2002-03-21T00:00:00"))
                .enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                assertTrue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                assertFalse(t.getMessage(), (t != null) );
            }
        });
    }

    @Test
    public void delete_isCorrect() throws Exception {
        UserRetrofitServices mAPIUserService = ApiUtils.getAPIUserService();
        mAPIUserService.remove(4905).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                assertTrue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                assertFalse(t.getMessage(), (t != null) );
            }
        });
    }

}
