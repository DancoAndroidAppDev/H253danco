package com.example.danco.homework2.h252danco.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.danco.homework2.h252danco.DummyContent;
import com.example.danco.homework2.h252danco.R;
import com.example.danco.homework2.h252danco.fragment.ContactDetailFragment;

public class ContactDetailActivity extends ActionBarActivity {

    private static final String CONTACT_DETAIL_FRAG = "contact_detail";


    public static Intent buildIntent(Context context, DummyContent.DummyItem item) {
        Intent intent = new Intent(context, ContactDetailActivity.class);
        intent.putExtra("ITEM", item);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        // Setup toolbar and configure the "up" button
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_contact_detail));
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        if (savedInstanceState == null) {
            DummyContent.DummyItem item = getIntent().getParcelableExtra("ITEM");
            ContactDetailFragment fragment = ContactDetailFragment.newInstance(item);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contactDetailContainer, fragment, CONTACT_DETAIL_FRAG)
                    .commit();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {

            // Process the up button
            case android.R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(this);
                // Using single top/clear top to not destroy the existing activity if it exists in
                // the stack. Using flags is more flexible than the manifest as you can implement
                // on an as needed basis. Also clear top is unable to be declared in manifest.
                // See Tasks and Back Stack in the android documentation for more details
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                NavUtils.navigateUpTo(this, intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact_detail, menu);
        return true;
    }
}
