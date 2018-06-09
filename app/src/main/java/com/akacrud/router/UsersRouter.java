package com.akacrud.router;

import android.app.Activity;
import android.content.Intent;

import com.akacrud.entity.model.User;
import com.akacrud.presenter.UsersContracts;
import com.akacrud.view.activities.UserRegisterActivity;

/**
 * Created by luisvespa on 06/09/18.
 */

public class UsersRouter implements UsersContracts.Router {

    private Activity activity;
    private int request_CreateCode = 1;
    private int request_UpdateCode = 2;

    public UsersRouter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void unRegister() {
        activity = null;
    }

    @Override
    public void presentUserRegisterScreen() {
        Intent intent = new Intent(activity, UserRegisterActivity.class);
        activity.startActivityForResult(intent, request_CreateCode);
    }

    @Override
    public void editCurrentUserScreen(User user) {
        Intent intent = new Intent(activity, UserRegisterActivity.class);
        intent.putExtra("mode_update", true);
        intent.putExtra("user", user);
        activity.startActivityForResult(intent, request_UpdateCode);

    }
}
