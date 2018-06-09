package com.akacrud.presenter;

import android.app.Activity;
import android.support.v7.view.ActionMode;
import android.util.Log;

import com.akacrud.entity.model.User;
import com.akacrud.interactor.UsersInteractor;
import com.akacrud.router.UsersRouter;

import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by luisvespa on 12/17/17.
 */

public class UsersPresenter extends Presenter<UsersContracts.View> implements UsersContracts.Presenter {

    private UsersInteractor interactor;
    private UsersContracts.Router router;

    public UsersPresenter(UsersInteractor interactor, UsersRouter usersRouter) {
        this.interactor = interactor;
        this.router = usersRouter;
    }

    @Override
    public void onDestroy() {
        this.interactor.unRegister();
        this.interactor = null;
        this.router.unRegister();
        this.router = null;
    }

    @Override
    public void getAllUsers() {
        getView().showLoading(true);

        interactor.getUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableSingleObserver<List<User>>() {
                @Override
                public void onSuccess(List<User> users) {
                    getView().showLoading(false);
                    if (!users.isEmpty() && users.size() > 0) {
                        getView().showLoading(false);
                        getView().renderUsers(users);
                    } else {
                        getView().showUsersNotFoundMessage(true);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    Log.e(TAG, "onError: " + e.getMessage());
                    getView().showLoading(false);
                    getView().showConnectionErrorMessage(true);
                }
            });

    }

    @Override
    public void remove(int id, final ActionMode mode) {
        getView().showLoading(true);
        interactor.remove(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableCompletableObserver() {
                @Override
                public void onComplete() {
                    getView().showLoading(false);
                    mode.finish();
                }

                @Override
                public void onError(Throwable e) {
                    getView().showLoading(false);
                    Log.d(TAG, "onError " + e.getMessage());
                    getView().showConnectionErrorMessage(true);
                }
            });

    }

    @Override
    public void goToUserRegisterScreen() {
        router.presentUserRegisterScreen();
    }

    @Override
    public void goToEditCurrentUserScreen(User user) {
        router.editCurrentUserScreen(user);
    }

}
