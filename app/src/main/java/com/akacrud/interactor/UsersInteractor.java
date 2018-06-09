package com.akacrud.interactor;

import com.akacrud.entity.api.client.UserService;
import com.akacrud.entity.model.User;
import com.akacrud.presenter.UsersContracts;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by luisvespa on 12/17/17.
 */

public class UsersInteractor implements UsersContracts.Interactor {

    UserService userService;

    public UsersInteractor(UserService userService) {
        this.userService = userService;
    }

    public Single<List<User>> getUsers() { return userService.getUsers(); }

    public Completable create(User user) { return userService.create(user); }

    public Single<User> update(User user) {
        return userService.update(user);
    }

    public Completable remove(int id) {
        return userService.remove(id);
    }

    @Override
    public void unRegister() {

    }
}
