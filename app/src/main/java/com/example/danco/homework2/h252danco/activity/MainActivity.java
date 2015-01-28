package com.example.danco.homework2.h252danco.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.danco.homework2.h252danco.R;
import com.example.danco.homework2.h252danco.fragment.ContactListFragment;
import com.example.danco.homework2.h252danco.fragment.ContactsFragment;
import com.example.danco.homework2.h252danco.fragment.DynamicGridViewFragment;


public class MainActivity extends ActionBarActivity
        implements AdapterView.OnItemClickListener {

    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    private static final int SETTINGS_REQUEST = 600;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ListView drawerList;

    // Using 1 as default since the header view in list will cause all items to shift by 1
    private int selectedPosition = 1;
    private String[] titleArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleArray = getResources().getStringArray(R.array.drawerItems);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        boolean initializeContentView = true;
        if (savedInstanceState != null) {
            selectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION, selectedPosition);
            initializeContentView = false;
        }

        setupDrawerLayout(toolbar);
        setupDrawerList();

        // Setup initial title. Done after processing saved instance state
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(titleArray[selectedPosition - 1]);

        // If no saved state, setup the default fragment view
        if (initializeContentView) {
            updateContentView();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, selectedPosition);
    }

    private void setupDrawerLayout(Toolbar toolbar) {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Since theme indicates app is drawing status bar area, this method is how to do this
        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));

        // set a custom shadow that overlays the main content when the drawer opens
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // Enable Nav Button (menu) to be shown
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        drawerToggle = new ActionBarDrawerToggle(
                this,                    /* host Activity */
                drawerLayout,                    /* DrawerLayout object */
                toolbar,             /* toolbar to use for managing nav button */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        );

        drawerLayout.setDrawerListener(drawerToggle);
    }

    private void setupDrawerList() {
        drawerList = (ListView) findViewById(R.id.navigation_drawer);
        View header = LayoutInflater.from(this).inflate(R.layout.drawer_header, drawerList, false);
        drawerList.addHeaderView(header, null, false);

        // The list by default will not draw under the status bar, the app needs to offset the top
        // of the list so it is "under" status bar. To do this we need to adjust the top margin
        // to be negative. Negative margins are not used often, but primarily used when you want
        // a view to encroach on the space of another view.
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) drawerList.getLayoutParams();
        params.topMargin = -1 * getStatusBarHeight();

        // Setup our list. the "Activated" layout is the trick I didn't quite remember originally
        // for the homework2. Using this will allow you to initially "highlight" an item in a list.
        // However in some cases you will need to construct a "custom" view to support your content
        // or design. You may or may not want to also use or adjust the activatedBackgroundIndicator
        // in your theme.  In AS you can go directly to an asset by right clicking and "Go To"->
        // "Declaration". There is a keyboard option as well which is shown in the menu. It varies
        // by platform so I'm not listing it here.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                titleArray);

        drawerList.setAdapter(adapter);
        // Mark our row as "activated"
        drawerList.setItemChecked(selectedPosition, true);
        drawerList.setOnItemClickListener(this);
    }

    /**
     * Retrieve the status bar height. By default this is a dimen that is not public, so
     * have to manually retrieve. Use a default fallback if fails.
     *
     * @return height of status bar
     */
    private int getStatusBarHeight() {
        int resId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId == 0) {
            resId = R.dimen.default_status_bar_height;
        }
        return getResources().getDimensionPixelSize(resId);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        drawerList.setItemChecked(position, true);
        drawerLayout.closeDrawer(drawerList);

        if (selectedPosition != position) {
            selectedPosition = position;
            updateContentView();
        }
    }

    private void updateContentView() {

        Fragment fragment = null;

        // subtracting 1 to account for "header" view in list
        int adjustedPosition = selectedPosition - 1;
        switch (adjustedPosition) {
            case 0:
                fragment = ContactsFragment.newInstance();
                break;
            case 1:
                fragment = DynamicGridViewFragment.newInstance();
                break;
            case 2:
                startActivityForResult(SettingsActivity.buildIntent(this), SETTINGS_REQUEST);
                break;
        }
        getSupportActionBar().setTitle(titleArray[adjustedPosition]);

        // Theoretically you will never have a null fragment, but this
        // can help by avoiding a crash if you add an item in list, but
        // forget to add to this method.
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment, "FRAG")
                    .commit();
        }
    }
}
