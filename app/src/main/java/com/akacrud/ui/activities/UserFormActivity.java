package com.akacrud.ui.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.akacrud.R;
import com.akacrud.controller.UserController;
import com.akacrud.model.User;
import com.akacrud.util.AlertDateDialog;
import com.akacrud.util.CommonUtils;
import butterknife.BindView;
import butterknife.ButterKnife;

public class UserFormActivity extends AppCompatActivity {

    private static String TAG = UserFormActivity.class.getSimpleName();

    private Activity mActivity;
    @BindView(R.id.linearLayoutUser) View mView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progressBar) ProgressBar mProgressView;
    @BindView(R.id.textViewName) TextView textViewName;
    @BindView(R.id.textViewBirthDate) TextView textViewBirthDate;

    private boolean mode_update = false;
    // Class Manager of services customer.
    private UserController userController;

    // Entity of the user
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);
        ButterKnife.bind(this);

        mActivity = this;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_back_arraw));
        getSupportActionBar().setTitle(R.string.title_activity_user_form_create);

        textViewBirthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDateDialog d = new AlertDateDialog(view);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                d.show(ft, "DatePicker");
            }
        });

        // Create a new instance of UserController class for manage user data
        userController = new UserController(mActivity);

        // get the intent object
        Intent intent = getIntent();
        // get the value mode_update
        mode_update = intent.getBooleanExtra("mode_update", false);
        if (mode_update) {
            // se activa la animacion del Loader
            showProgress(true);
            setupUser((User) intent.getSerializableExtra("user"));
        }

    }

    private void attemptSave() {

        String name = textViewName.getText().toString();
        String birthdate = textViewBirthDate.getText().toString();

        boolean cancel = false;
        String mMessage = "";

        // Check for a valid email address.
        if (TextUtils.isEmpty(birthdate)) {
            cancel = true;
            mMessage = getResources().getString(R.string.field_birthdate_required);
        } else if (TextUtils.isEmpty(name)) {
            cancel = true;
            mMessage = getResources().getString(R.string.field_name_required);
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            CommonUtils.showSnackBar(mActivity, mView, mMessage);
        } else {
            if (!mode_update) {
                user = new User();
            }
            user.setName(name);
            user.setBirthdate(birthdate);

            if (mode_update)
                userController.update(this, mView, user);
            else
                userController.create(this, mView, user);
        }

    }

    private void setupUser(User user) {
        this.user = user;

        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewBirthDate = (TextView) findViewById(R.id.textViewBirthDate);

        textViewName.setText(user.getName());
        textViewBirthDate.setText(user.getBirthdate());

        showProgress(false);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_user_form, menu);

        MenuItem menuItemSave = menu.findItem(R.id.action_save);
        menuItemSave.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                attemptSave();
                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.activity_back_in, R.anim.activity_back_out);
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }

}
