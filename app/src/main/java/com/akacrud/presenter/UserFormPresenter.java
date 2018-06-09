package com.akacrud.presenter;

import android.content.Intent;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.widget.Toast;

import com.akacrud.entity.model.User;
import com.akacrud.interactor.UsersInteractor;
import com.akacrud.view.activities.UserFormActivity;
import com.akacrud.view.util.CommonUtils;

import org.json.JSONObject;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

/**
 * Created by luisvespa on 12/17/17.
 */

public class UserFormPresenter extends Presenter<UserFormPresenter.View> {

    private UsersInteractor interactor;

    public UserFormPresenter(UsersInteractor interactor) {
        this.interactor = interactor;
    }

    public void create(final UserFormActivity activity, User user) {
        getView().showLoading(true);
        CommonUtils.hideKeyBoard(activity);

        interactor.create(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableCompletableObserver() {
                @Override
                public void onComplete() {
                    // The transaction is completeds
                    Intent data = new Intent();
                    activity.setResult(RESULT_OK, data);
                    activity.finish();
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

    public void update(final UserFormActivity activity, User user) {
        getView().showLoading(true);
        CommonUtils.hideKeyBoard(activity);

        interactor.update(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<User>() {
                    public void onSuccess(User user) {
                        // The transaction is successful
                        Intent data = new Intent();
                        activity.setResult(RESULT_OK, data);
                        activity.finish();
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

    public interface View extends Presenter.View {

        void showLoading(boolean show);

        void showSnackBarMessage(String message);

    }
}
