package com.example.danco.homework2.h252danco.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.danco.homework2.h252danco.DummyContent;
import com.example.danco.homework2.h252danco.R;
import com.example.danco.homework2.h252danco.fragment.ContactDetailFragment;
import com.example.danco.homework2.h252danco.fragment.ContactListFragment;


public class ContactListActivity extends ActionBarActivity
        implements ContactListFragment.ItemFragmentListener {

    private static final String TAG = ContactListActivity.class.getName();

    private static final String DETAIL_FRAGMENT = "detail";
    private static final int DYNAMIC_REQUEST = 100;

    private boolean haveDynamicFragment = false;

    public static Intent buildIntent(Context context) {
        Log.i(TAG, "building ContactListActivity intent");
        return new Intent(context, ContactListActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(R.string.app_name);

        haveDynamicFragment = findViewById(R.id.contact_detail_container) != null;

        if (haveDynamicFragment) {
            ContactDetailFragment contactDetail = (ContactDetailFragment)
                    getSupportFragmentManager().findFragmentByTag(DETAIL_FRAGMENT);
            if (contactDetail == null) {
                contactDetail = ContactDetailFragment.newInstance(DummyContent.ITEMS.get(0));
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.contact_detail_container, contactDetail, DETAIL_FRAGMENT)
                        .commit();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onUpdateDynamicFragment(DummyContent.DummyItem item) {
        if (haveDynamicFragment) {
            ContactDetailFragment fragment =
                    ContactDetailFragment.newInstance(item);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contact_detail_container, fragment, DETAIL_FRAGMENT)
                    .commit();

        } else {
            startActivity(ContactDetailActivity.buildIntent(this, item));
        }
    }
}
