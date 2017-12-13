package com.akacrud.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akacrud.R;
import com.akacrud.model.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by luisvespa on 12/13/17.
 */

public class RecyclerAdapterUser extends RecyclerView.Adapter<RecyclerAdapterUser.ViewHolder> {
    private Activity mActivity;
    private FragmentManager mFragmentManager;
    private List<User> userList;
    private static ClickListener clickListener;

    public RecyclerAdapterUser(Activity context, List<User> userList) {
        this.userList = userList;
        this.mActivity = context;
    }


    @Override
    public RecyclerAdapterUser.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_user, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(final RecyclerAdapterUser.ViewHolder holder, final int position) {
        //Typeface fontTypeRegular = Typeface.createFromAsset(mActivity.getAssets(), SessionPreferences.GREAT_VIBES_REGULAR);

        if (holder.textViewName != null)
            holder.textViewName.setText(userList.get(position).getName());

        if (holder.textViewBirthdate != null)
            holder.textViewBirthdate.setText(userList.get(position).getBirthdate());

    }

    public void setFilter(List<User> userListFiltered) {
        userList = new LinkedList<>();
        userList.addAll(userListFiltered);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView textViewName;
        public TextView textViewBirthdate;
        public CardView cardView;

        public ViewHolder(View mView) {
            super(mView);
            mView.setOnClickListener(this);
            textViewName = (TextView) mView.findViewById(R.id.textViewName);
            textViewBirthdate = (TextView) mView.findViewById(R.id.textViewBirthdate);
            cardView = (CardView) mView.findViewById(R.id.cardView);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        RecyclerAdapterUser.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);

    }
}
