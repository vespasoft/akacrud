package com.akacrud.ui.activities;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.akacrud.R;
import com.akacrud.ui.fragments.SettingFragment;
import com.akacrud.ui.fragments.UserFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private Context mContext;
    private Toolbar toolbar;
    private FrameLayout containerBody;
    private RelativeLayout containerMain;
    private boolean modeEdit = false;
    private int current_fragment = 0;
    private Fragment fragment = null;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        containerBody = (FrameLayout)findViewById(R.id.container_body);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Show the view users by default
        displayView(0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        tb.inflateMenu(R.menu.main);


        tb.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });
        SearchView searchView = (SearchView) tb.getMenu().findItem(R.id.action_search).getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                //String textToFind = s.toUpperCase();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                try {
                    if ( current_fragment==0 && s.length() >= 0) {
                        String findByName = s.toLowerCase();
                        Log.d("MainActivity", "Search on fragment " + findByName);
                        if (current_fragment==0) {
                            UserFragment userfragment = (UserFragment) fragment;
                            userfragment.setFilter(findByName);
                        }
                    }
                } catch (Exception ex) {

                }


                return false;
            }
        });


        return true;
    }

    //TODO: This method is in disuse if you do not plan to use more can be eliminated
    // This method show and hide the Toolbar when the ActionMode is activated
    public void showToolbar(boolean show) {
        if (show) {
            toolbar.setVisibility(View.VISIBLE);
            //toolbar.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
        } else {
            toolbar.setVisibility(View.GONE);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_users) {
            // Handle the users action
            displayView(0);
        } else if (id == R.id.nav_settings) {
            // Handle the settings action
            displayView(1);
        } else if (id == R.id.nav_exit) {
            // Handle the exit action
            displayView(2);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClick(View v) {
        final int id = v.getId();
        switch (id) {
            case R.id.buttonRetry:
                displayView(0);
                break;

        }
    }

    /*
     *   Method that shows the fragments on the container
     */
    private void displayView(int position) {
        String title = getString(R.string.app_name);
        current_fragment = position;
        switch (position) {
            case 0:
                containerBody.setVisibility(View.VISIBLE);
                fragment = new UserFragment();
                title = getString(R.string.title_fragment_users);
                break;
            case 1:
                containerBody.setVisibility(View.VISIBLE);
                fragment = new SettingFragment();
                title = getString(R.string.title_fragment_settings);
                break;
            case 2:
                finish();
                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

        }

        // set the toolbar title
        getSupportActionBar().setTitle(title);
    }

}
