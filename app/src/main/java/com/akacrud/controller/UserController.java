package com.akacrud.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.Display;
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

import static android.app.Activity.RESULT_OK;

/**
 * Created by luisvespa on 12/13/17.
 */

public class UserController {
    private static String TAG = UserController.class.getSimpleName();

    Context mContext;
    Activity mActivity;
    UserServices mAPIUserService;
    List<User> users;

    public UserController(Activity activity) {

        mActivity = activity;
        mContext = activity;
    }

    public UserController(List<User> users) {
        this.users = users;
    }

    public void getAll(final UserFragment fragment, final View mView) {
        users = new LinkedList<>();
        mAPIUserService = ApiUtils.getAPIUserService();
        mAPIUserService.getAll().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                if (response.isSuccessful()) {
                    users = response.body();
                    fragment.setupUsers(users);
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
                    Intent data = new Intent();
                    activity.setResult(RESULT_OK, data);
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


    public void update (final UserFormActivity activity, final View mView, User user) {
        activity.showProgress(true);
        CommonUtils.hideKeyBoard(activity);

        mAPIUserService = ApiUtils.getAPIUserService();
        mAPIUserService.update(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                activity.showProgress(false);
                if (response.isSuccessful()) {
                    // The transaction is successful
                    Intent data = new Intent();
                    activity.setResult(RESULT_OK, data);
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


    public void remove (final UserFragment fragment, final View mView, final ActionMode mode, int id) {
        fragment.showProgress(true);
        mAPIUserService = ApiUtils.getAPIUserService();
        mAPIUserService.remove(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "User is deleted successful ");
                    mode.finish();
                } else {
                    Log.d(TAG, "Error of server");
                    fragment.showProgress(false);
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        CommonUtils.showSnackBar(mActivity, mView, jObjError.getString("message"));
                        Log.i(TAG, "post error of the API. " + jObjError.getString("message"));
                    } catch (Exception e) {
                        Toast.makeText(mActivity, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // fail internet connection or server connection
                Log.d(TAG, "onFailure " + t.toString());
                fragment.showProgress(false);
                fragment.showErrorInternetConnection(true);
                CommonUtils.showSnackBar(mActivity, mView, mActivity.getResources().getString(R.string.message_error_internet));
            }
        });

    }

}
