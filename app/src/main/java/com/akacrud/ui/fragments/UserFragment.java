package com.akacrud.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;
import com.akacrud.R;
import com.akacrud.controller.UserController;
import com.akacrud.model.User;
import com.akacrud.ui.activities.UserFormActivity;
import com.akacrud.ui.adapter.RecyclerAdapterUser;
import com.akacrud.ui.listener.ClickListener;
import com.akacrud.ui.listener.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {
    private static String TAG = UserFragment.class.getSimpleName();

    private Context mContext;
    private Activity mActivity;
    private View layout;
    private View mProgressView;
    private RecyclerView mRecyclerView;
    private RecyclerAdapterUser mAdapter;
    private CardView cardViewCloudOff;
    private List<User> userList;
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
        mActivity = getActivity();
        mContext = getContext();

        mProgressView = layout.findViewById(R.id.progressBar);
        mRecyclerView = (RecyclerView) layout.findViewById(R.id.recycler_view);

        showProgress(true);

        // Create a new instance of UserController class for manage user data
        usersController = new UserController(mActivity);
        usersController.getAll(this, layout);

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
     *  Get the user list
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

    public void setFilter(String query) {
        mAdapter.setFilter(filter(userList, query));
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

}
