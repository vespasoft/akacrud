package com.akacrud.entity.api.client;

import com.akacrud.entity.api.retrofit.ApiUtils;
import com.akacrud.entity.api.retrofit.RetrofitClient;
import com.akacrud.entity.model.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by luisvespa on 12/17/17.
 */

public class UserClient extends RetrofitClient implements UserService {

    @Override
    public Single<List<User>> getUsers() {
        return ApiUtils.getAPIUserService().getUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable create(User user) {
        return ApiUtils.getAPIUserService().create(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Single<User> update(User user) {
        return ApiUtils.getAPIUserService().update(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Completable remove(int id) {
        return ApiUtils.getAPIUserService().remove(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
