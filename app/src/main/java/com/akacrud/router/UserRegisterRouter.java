package com.akacrud.router;

import android.app.Activity;
import android.content.Intent;

import com.akacrud.R;
import com.akacrud.presenter.UserRegisterContracts;
import com.akacrud.presenter.UsersContracts;

import static android.app.Activity.RESULT_OK;

/**
 * Created by luisvespa on 06/09/18.
 */

public class UserRegisterRouter implements UserRegisterContracts.Router {

    private Activity activity;

    public UserRegisterRouter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void unRegister() {

    }

    @Override
    public void goToBackScreen() {
        activity.finish();
        activity.overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
    }

    @Override
    public void goToBackWithResult() {
        Intent data = new Intent();
        activity.setResult(RESULT_OK, data);
        activity.finish();
    }
}
