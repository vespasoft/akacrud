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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;
import com.akacrud.R;
import com.akacrud.controller.UserController;
import com.akacrud.model.User;
import com.akacrud.ui.activities.MainActivity;
import com.akacrud.ui.activities.UserFormActivity;
import com.akacrud.ui.adapter.RecyclerAdapterUser;
import com.akacrud.ui.helper.DividerItemDecoration;
import com.akacrud.ui.listener.ClickListener;
import com.akacrud.ui.listener.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

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
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActionModeCallback actionModeCallback;
    private ActionMode actionMode;
    /**
     * Class Manager of services customer.
     */
    private UserController usersController;

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

        swipeRefreshLayout = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);

        actionModeCallback = new ActionModeCallback();

        // show loader and fetch messages
        swipeRefreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        getUsers();
                    }
                }
        );


        FloatingActionButton fab = (FloatingActionButton) layout.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: En este evento se debe llamar a un nuevo activity para registrar un nuevo usuario
                Intent intent = new Intent(mActivity, UserFormActivity.class);
                startActivity(intent);
            }
        });

        return layout;
    }

    /**
     * Fetches mail messages by making HTTP request
     * url: http://api.androidhive.info/json/inbox.json
     */
    private void getUsers() {
        swipeRefreshLayout.setRefreshing(true);
        showProgress(true);
        usersController = new UserController(mActivity);
        usersController.getAll(this, layout);
    }

    /**
     * Shows the user data in the RecyclerView
     */
    public void setupUsers(List<User> data) {
        this.users = data;

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
        mAdapter = new RecyclerAdapterUser(users);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(mActivity, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(mContext, mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(mContext, "Has presionado el usuario " + users.get(position).getName() , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                // Redraw the old selection and the new
                mAdapter.notifyItemChanged(position);
                mAdapter.setSelectedItem(position);
                mAdapter.notifyItemChanged(position);
                mAdapter.notifyDataSetChanged();
                // long press is performed, enable action mode
                enableActionMode(position);
            }
        }));

        swipeRefreshLayout.setRefreshing(false);
        showProgress(false);
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
        Log.d("filter", "query: "+ filterPatter);
        if (filterPatter.length()==0) {
            userListFiltered.addAll(userList);
        } else {
            for (int i=0; i < userList.size(); i++) {
                User user = userList.get(i);
                Log.d("filter", "Compare "+ user.getName().toLowerCase() + " to " + filterPatter);
                if (user.getName().toLowerCase().contains(filterPatter)) {
                    userListFiltered.add(user);
                }
            }
        }
        return userListFiltered;
    }

    // deleting the messages from recycler view
    public void deleteMessages() {

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
        getUsers();
    }

    private void enableActionMode(int position) {
        if (actionMode == null) {
            actionMode = mActivity.startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }

    private void toggleSelection(int position) {
        mAdapter.setSelectedItem(position);

        if (position < 0) {
            actionMode.finish();
        } else {
            //mActivity.showToolbar(false);
            actionMode.setTitle(String.valueOf(position));
            actionMode.invalidate();
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_action_mode, menu);

            // disable swipe refresh if action mode is enabled
            swipeRefreshLayout.setEnabled(false);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {

                case R.id.action_delete:
                    // delete all the selected messages
                    // deleteMessages();
                    mode.finish();
                    //mActivity.showToolbar(true);
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
            swipeRefreshLayout.setEnabled(true);
            actionMode = null;
            //mActivity.showToolbar(true);
            mRecyclerView.post(new Runnable() {
                @Override
                public void run() {
                    //mAdapter.resetAnimationIndex();
                    //mAdapter.notifyDataSetChanged();
                }
            });
        }
    }

}
