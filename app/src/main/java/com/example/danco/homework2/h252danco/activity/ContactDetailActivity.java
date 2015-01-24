package com.example.danco.homework2.h252danco.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.danco.homework2.h252danco.DummyContent;
import com.example.danco.homework2.h252danco.R;
import com.example.danco.homework2.h252danco.fragment.ContactDetailFragment;

public class ContactDetailActivity extends ActionBarActivity {

    private static final String FRAGMENT_TAG = "ContactDetails";
    private static DummyContent.DummyItem ITEM;

    public static Intent buildIntent(Context context, DummyContent.DummyItem item) {
        Intent intent = new Intent(context, ContactDetailActivity.class);
        // item clicked information handled here
        ITEM = item;
        intent.putExtra(item.id, item);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        if (savedInstanceState == null) {
            ContactDetailFragment fragment = ContactDetailFragment.newInstance(ITEM);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.dynamicFragmentContainer, fragment, FRAGMENT_TAG)
                    .commit();
        }
    }
}
