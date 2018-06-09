package com.akacrud.presenter;

import android.content.Intent;

import com.akacrud.entity.model.User;
import com.akacrud.view.activities.UserRegisterActivity;

/**
 * Created by luisvespa on 06/08/18.
 */

public class UserRegisterContracts {

    public interface View extends com.akacrud.presenter.Presenter.View {

        void showLoading(boolean show);

        void showSnackBarMessage(String message);

    }

    public interface Presenter  {
        void onDestroy();

        void create(final UserRegisterActivity activity, User user);

        void update(final UserRegisterActivity activity, User user);

        void goToBackScreen();

        void goToBackWithResult();
    }

    public interface Router {
        void unRegister();

        void goToBackScreen();

        void goToBackWithResult();
    }
}
