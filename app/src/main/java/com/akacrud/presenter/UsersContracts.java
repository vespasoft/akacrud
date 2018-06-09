package com.akacrud.presenter;

import android.support.v7.view.ActionMode;

import com.akacrud.entity.model.User;

import java.util.List;

/**
 * Created by luisvespa on 06/08/18.
 */

public class UsersContracts {

    public interface View extends com.akacrud.presenter.Presenter.View {

        void onDestroy();

        void showLoading(boolean show);

        void showUsersNotFoundMessage(boolean show);

        void showConnectionErrorMessage(boolean show);

        void renderUsers(List<User> users);
    }

    public interface Presenter  {
        void onDestroy();

        void getAllUsers();

        void remove(int id, final ActionMode mode);

        void goToUserRegisterScreen();

        void goToEditCurrentUserScreen(User user);
    }

    public interface Interactor {
        void unRegister();
    }

    public interface Router {
        void unRegister();

        void presentUserRegisterScreen();

        void editCurrentUserScreen(User user);
    }
}
