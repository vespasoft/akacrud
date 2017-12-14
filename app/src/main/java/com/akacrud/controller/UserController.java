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

    UserFragment userFragment;
    Context mContext;
    Activity mActivity;
    UserServices mAPIUserService;
    List<User> usersList;

    public UserController(UserFragment fragment, Activity activity) {
        userFragment = fragment;
        mActivity = activity;
        mContext = activity;
    }

    public UserController(List<User> usersList) {
        this.usersList = usersList;
    }

    public void getAll() {
        usersList = new LinkedList<>();
        mAPIUserService = ApiUtils.getAPIUserService();
        mAPIUserService.getAll().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                if (response.isSuccessful()) {
                    usersList = response.body();
                    userFragment.setupUsers(usersList);
                } else {
                    userFragment.showProgress(false);
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        CommonUtils.showSnackBar(mActivity, userFragment.layout, jObjError.getString("message"));
                        Log.i(TAG, "post error to API. " + jObjError.getString("message"));
                    } catch (Exception e) {
                        Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                userFragment.showErrorInternetConnection(true);
                CommonUtils.showSnackBar(mActivity, userFragment.layout, mActivity.getResources().getString(R.string.message_error_internet));

            }
        });

    }

}
