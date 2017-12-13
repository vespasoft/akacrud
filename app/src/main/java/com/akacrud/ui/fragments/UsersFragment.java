package com.akacrud.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.akacrud.R;
import com.akacrud.controller.UserController;
import com.akacrud.model.User;
import com.akacrud.ui.adapter.RecyclerAdapterUser;
import com.akacrud.ui.listener.ClickListener;
import com.akacrud.ui.listener.RecyclerTouchListener;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class UsersFragment extends Fragment {
    private static String TAG = UsersFragment.class.getSimpleName();

    private View mView;
    private Context mContext;
    private Activity mActivity;
    private View mProgressView;
    private View mUserFrameLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerAdapterUser mAdapter;
    private View containerView;
    private List<User> userList;
    /**
     * Class Manager of services customer.
     */
    private UserController usersController;

    public UsersFragment() {
        // Required empty public constructor
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
        mContext = getContext();

        mUserFrameLayout = layout.findViewById(R.id.user_frame_layout);
        mProgressView = layout.findViewById(R.id.progress_bar);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view);

        showProgress(true);

        /*
        * Create a new instance of UserController class for manage user data
        */
        usersController = new UserController();

        setupUsers( usersController.getAll() ); ;

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
    public List<User> getUserList() {
        return userList;
    }

    /**
     * Shows the user data in the RecyclerView
     */
    public void setupUsers(List<User> data) {
        this.userList = data;

        LinearLayoutManager layoutManager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new RecyclerAdapterUser(mActivity, userList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(mContext, mRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(mContext, "Has presionado el usuario " + userList.get(position).getName() , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

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

}
