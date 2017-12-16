package com.akacrud.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import com.akacrud.R;
import com.akacrud.controller.UserController;
import com.akacrud.model.User;
import com.akacrud.ui.activities.MainActivity;
import com.akacrud.ui.activities.UserFormActivity;
import com.akacrud.ui.adapter.RecyclerAdapterUser;
import com.akacrud.ui.listener.ClickListener;
import com.akacrud.ui.listener.RecyclerTouchListener;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {
    private static String TAG = UserFragment.class.getSimpleName();

    private Context mContext;
    private MainActivity mActivity;
    private View layout;
    private View mProgressView;
    private RecyclerView mRecyclerView;
    private RecyclerAdapterUser mAdapter;
    private CardView cardViewCloudOff;
    private List<User> users;
    private ActionModeCallback actionModeCallback;
    private ActionMode actionMode;
    /**
     * Class Manager of services customer.
     */
    private UserController usersController;

    private int request_CreateCode = 1;
    private int request_UpdateCode = 2;

    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_users, container, false);
        mActivity = (MainActivity) getActivity();
        mContext = getContext();

        mProgressView = layout.findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view);

        usersController = new UserController(mActivity);

        actionModeCallback = new ActionModeCallback();

        FloatingActionButton fab = (FloatingActionButton) layout.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, UserFormActivity.class);
                startActivityForResult(intent, request_CreateCode);
            }
        });

        getAllUsers();

        return layout;
    }

    /**
     * Fetches user list by making HTTP request
     */
    public void getAllUsers() {
        showProgress(true);
        usersController.getAll(this, layout);
    }

    // Edit the selected user from recycler view
    public void editCurrentUser(ActionMode mode) {
        try {
            User userSelected = mAdapter.getSelectedItem();
            Intent intent = new Intent(mActivity, UserFormActivity.class);
            // it is opened the user form activity in update mode
            intent.putExtra("mode_update", true);
            // a serializable object is passed with the user's data
            intent.putExtra("user", userSelected);
            // start activity
            startActivityForResult(intent, request_UpdateCode);
        } catch (Exception ex) {
            Log.d(TAG, "Error to delete a user "+ ex.toString());
        }
    }

    // Delete the selected user from recycler view
    public void deleteCurrentUser(ActionMode mode) {
        try {
            // delete the selected item
            User userSelected = mAdapter.getSelectedItem();
            Log.d(TAG, "User to delete " + userSelected.getName());
            usersController.remove(UserFragment.this, layout, mode, userSelected.getId() );
        } catch (Exception ex) {
            Log.d(TAG, "Error to delete a user "+ ex.toString());
        }

    }

    /**
     * Shows the user data in the RecyclerView
     */
    public void setupUsers(List<User> data) {
        try {
            this.users = data;
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new RecyclerAdapterUser(users);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(mContext, mRecyclerView, new ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    Toast.makeText(mContext, "Has presionado el usuario " + users.get(position).getName() , Toast.LENGTH_LONG).show();
                }

                @Override
                public void onLongClick(View view, int position) {
                    try {
                        Toast.makeText(mContext, "Has seleccionado al usuario " + users.get(position).getName() , Toast.LENGTH_LONG).show();
                        // long press is performed, enable action mode
                        enableActionMode(position);
                    } catch (Exception ex){
                        Log.e(TAG, "error in onLongClick. " + ex.toString());
                    }
                }
            }));

            showProgress(false);
        } catch (Exception ex) {
            Log.e(TAG, "Error in setupusers method. " + ex.toString());
        }
    }

    public void setFilter(String query) {
        mAdapter.setFilter(filter(users, query));
    }

    /*
    This function goes through the current arrangement of users and compares the matches
    with the query inserted by the user. Matches are added in a
    new array called userListFiltered.
    */
    private List<User> filter (List<User> userList, String mQuery) {
        List<User> userListFiltered = new ArrayList<>();
        String filterPatter = mQuery.toLowerCase();
        Log.d(TAG, "filter query: "+ filterPatter);
        if (filterPatter.length()==0) {
            userListFiltered.addAll(userList);
        } else {
            for (int i=0; i < userList.size(); i++) {
                User user = userList.get(i);
                Log.d(TAG, "compare "+ user.getName().toLowerCase() + " to " + filterPatter);
                if (user.getName().toLowerCase().contains(filterPatter)) {
                    userListFiltered.add(user);
                }
            }
        }
        return userListFiltered;
    }

    public void showErrorInternetConnection(boolean show) {
        cardViewCloudOff = (CardView) mActivity.findViewById(R.id.cardViewCloudOff);
        cardViewCloudOff.setVisibility(show ? View.VISIBLE : View.GONE);
        cardViewCloudOff.startAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.fade_in));
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    public void showProgress(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    private void enableActionMode(int position) {
        if (actionMode == null) {
            actionMode = mActivity.startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }


    private void toggleSelection(int position) {
        mAdapter.setSelectedItem(position);
        mAdapter.notifyDataSetChanged();

        if (position < 0) {
            actionMode.finish();
        } else {
            //mActivity.showToolbar(false);
            actionMode.setTitle(String.valueOf(position));
            actionMode.invalidate();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == request_CreateCode) {
            if (resultCode==RESULT_OK) {
                // Update the users list after a user added
                getAllUsers();
            } else if (resultCode==RESULT_CANCELED) {
                // The user canceled the operation ...
                Log.e(TAG, "The user canceled the operation ...");
            }
        }

        if (requestCode == request_UpdateCode) {
            if (resultCode==RESULT_OK) {
                // Update the users list after a user added
                getAllUsers();
            } else if (resultCode==RESULT_CANCELED) {
                // The user canceled the operation ...
                Log.e(TAG, "The user canceled the operation ...");
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onRefresh() {
        // update the users list
        getAllUsers();
    }


    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);

            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {

                case R.id.action_edit:
                    editCurrentUser(mode);
                    return true;

                case R.id.action_delete:
                    deleteCurrentUser(mode);
                    return true;

                default:
                    mode.finish();
                    //mActivity.showToolbar(true);
                    return true;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mAdapter.setSelectedItem(-1);
            actionMode = null;
            //mActivity.showToolbar(true);
            mRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    //mAdapter.resetAnimationIndex();
                    //mAdapter.notifyDataSetChanged();
                    getAllUsers();
                }
            });
        }
    }

}
