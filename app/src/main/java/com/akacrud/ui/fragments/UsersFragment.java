package com.akacrud.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akacrud.R;
import com.akacrud.controller.UserController;
import com.akacrud.model.User;
import com.akacrud.ui.adapter.RecyclerAdapterUser;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UsersFragment.FragmentUserListener} interface
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class UsersFragment extends Fragment {
    private static String TAG = UsersFragment.class.getSimpleName();

    private View mView;
    private Activity mActivity;
    private View mProgressView;
    private View mUserFrameLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerAdapterUser mAdapter;
    private View containerView;
    private FragmentUserListener userListener;
    private List<User> userList;
    /**
     * Class Manager of services customer.
     */
    private UserController usersController;

    public UsersFragment() {
        // Required empty public constructor
    }

    public void setUserListener(FragmentUserListener listener) {
        this.userListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_users, container, false);
        containerView = layout;
        mActivity = getActivity();

        mUserFrameLayout = layout.findViewById(R.id.user_frame_layout);
        mProgressView = layout.findViewById(R.id.progress_bar);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view);

        showProgress(true);

        /*
        * Create a new instance of UserController class for manage user data
        */
        usersController = new UserController();

        setUsers( usersController.getAll() ); ;

        FloatingActionButton fab = (FloatingActionButton) layout.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: En este evento se debe llamar a un nuevo activity para registrar un nuevo usuario
                //Intent intent = new Intent(mActivity, CustomerActivity.class);
                //startActivity(intent);
            }
        });


        return layout;
    }

    /**
     * Get the user list
     */
    public List<User> getUsers() {
        return userList;
    }

    /**
     * Shows the user data in the RecyclerView
     */
    public void setUsers(List<User> data) {
        this.userList = data;

        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerAdapterUser(mActivity, userList);
        mRecyclerView.setAdapter(mAdapter);

        showProgress(false);
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

    public interface FragmentUserListener {
        public void onUserItemSelected(View view, int position);
    }
}
