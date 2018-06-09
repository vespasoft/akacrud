package com.akacrud.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akacrud.R;
import com.akacrud.entity.model.User;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by luisvespa on 12/13/17.
 */

public class RecyclerAdapterUser extends RecyclerView.Adapter<RecyclerAdapterUser.ViewHolder> {
    private List<User> users;

    // index is used to animate only the selected row
    // dirty fix, find a better solution
    private static int currentSelectedIndex = -1;

    public RecyclerAdapterUser(List<User> users) {
        this.users = users;
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
            holder.textViewName.setText(users.get(position).getName());

        if (holder.textViewBirthdate != null)
            holder.textViewBirthdate.setText(users.get(position).getBirthdate());

        holder.itemView.setSelected(currentSelectedIndex == position);

    }

    public User getSelectedItem() {
        return users.get(currentSelectedIndex);
    }

    public void setSelectedItem(int selectedItem) {
        this.currentSelectedIndex = selectedItem;
    }

    public void setFilter(List<User> userListFiltered) {
        users = new LinkedList<>();
        users.addAll(userListFiltered);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName;
        public TextView textViewBirthdate;


        public ViewHolder(View mView) {
            super(mView);
            textViewName = (TextView) mView.findViewById(R.id.textViewName);
            textViewBirthdate = (TextView) mView.findViewById(R.id.textViewBirthdate);

        }

    }

}
