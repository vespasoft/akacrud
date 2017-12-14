package com.akacrud.controller;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.akacrud.R;
import com.akacrud.model.User;
import com.akacrud.retrofit.ApiUtils;
import com.akacrud.retrofit.UserServices;
import com.akacrud.ui.activities.UserFormActivity;
import com.akacrud.ui.fragments.UserFragment;
import com.akacrud.util.CommonUtils;

import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by luisvespa on 12/13/17.
 */

public class UserController {
    private static String TAG = UserController.class.getSimpleName();

    Context mContext;
    Activity mActivity;
    UserServices mAPIUserService;
    List<User> usersList;

    public UserController(Activity activity) {

        mActivity = activity;
        mContext = activity;
    }

    public UserController(List<User> usersList) {
        this.usersList = usersList;
    }

    public void getAll(final UserFragment fragment, final View mView) {
        usersList = new LinkedList<>();
        mAPIUserService = ApiUtils.getAPIUserService();
        mAPIUserService.getAll().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                if (response.isSuccessful()) {
                    usersList = response.body();
                    fragment.setupUsers(usersList);
                } else {
                    fragment.showProgress(false);
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        CommonUtils.showSnackBar(mActivity, mView, jObjError.getString("message"));
                        Log.i(TAG, "post error to API. " + jObjError.getString("message"));
                    } catch (Exception e) {
                        Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                // fail internet connection or server connection
                fragment.showProgress(false);
                fragment.showErrorInternetConnection(true);
                CommonUtils.showSnackBar(mActivity, mView, mActivity.getResources().getString(R.string.message_error_internet));

            }
        });

    }

    public void create (final UserFormActivity activity, final View mView, User user) {
        activity.showProgress(true);
        CommonUtils.hideKeyBoard(activity);

        mAPIUserService = ApiUtils.getAPIUserService();
        mAPIUserService.create(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                activity.showProgress(false);
                if (response.isSuccessful()) {
                    // The transaction is successful
                    activity.finish();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        CommonUtils.showSnackBar(mActivity, mView, jObjError.getString("message"));
                    } catch (Exception e) {
                        Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // fail internet connection or server connection
                activity.showProgress(false);
                CommonUtils.showSnackBar(mActivity, mView, mActivity.getResources().getString(R.string.message_error_internet));
            }
        });
    }

}
