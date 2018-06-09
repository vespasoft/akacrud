package com.akacrud.presenter;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;
import com.akacrud.entity.model.User;
import com.akacrud.interactor.UsersInteractor;
import com.akacrud.router.UserRegisterRouter;
import com.akacrud.view.activities.UserRegisterActivity;
import com.akacrud.view.util.CommonUtils;

import org.json.JSONObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;

/**
 * Created by luisvespa on 12/17/17.
 */

public class UserRegisterPresenter extends Presenter<UserRegisterContracts.View> implements UserRegisterContracts.Presenter {

    private UsersInteractor interactor;
    private UserRegisterContracts.Router router;

    public UserRegisterPresenter(UsersInteractor interactor, UserRegisterRouter userRegisterRouter) {
        this.interactor = interactor;
        this.router = userRegisterRouter;
    }

    @Override
    public void onDestroy() {
        this.interactor.unRegister();
        this.interactor = null;
        this.router.unRegister();
        this.router = null;
    }

    public void create(final UserRegisterActivity activity, User user) {
        getView().showLoading(true);
        CommonUtils.hideKeyBoard(activity);

        interactor.create(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableCompletableObserver() {
                @Override
                public void onComplete() {
                    // The transaction is completeds
                    goToBackWithResult();
                }

                @Override
                public void onError(Throwable e) {
                    try {
                        JSONObject jObjError = new JSONObject(e.getMessage());
                        getView().showSnackBarMessage(jObjError.getString("message"));
                    } catch (Exception ex) {
                        Toast.makeText(activity, ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            });
    }

    public void update(final UserRegisterActivity activity, User user) {
        getView().showLoading(true);
        CommonUtils.hideKeyBoard(activity);

        interactor.update(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<User>() {
                    public void onSuccess(User user) {
                        // The transaction is successful
                        goToBackWithResult();
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            JSONObject jObjError = new JSONObject(e.getMessage());
                            getView().showSnackBarMessage(jObjError.getString("message"));
                        } catch (Exception ex) {
                            Toast.makeText(activity, ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    public void goToBackScreen() {
        router.goToBackScreen();
    }

    @Override
    public void goToBackWithResult() {
        router.goToBackWithResult();
    }

}
