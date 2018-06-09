package com.akacrud.entity.api.client;

import com.akacrud.entity.model.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by luisvespa on 12/17/17.
 */

public interface UserService {

    Single<List<User>> getUsers();

    Completable create(User user);

    Single<User> update(User user);

    Completable remove(int id);
}
