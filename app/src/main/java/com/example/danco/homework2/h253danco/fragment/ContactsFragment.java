package com.example.danco.homework2.h253danco.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.danco.homework2.h253danco.DummyContent;
import com.example.danco.homework2.h253danco.R;
import com.example.danco.homework2.h253danco.activity.AddContactActivity;
import com.example.danco.homework2.h253danco.activity.ContactDetailActivity;

/**
 * Created by danco on 1/27/15.
 */
public class ContactsFragment extends Fragment
        implements ContactDetailFragment.ContactDetailFragmentListener,
        ContactListFragment.ContactListFragmentListener {

    private boolean haveDynamicFragment = false;

    private static final String CONTACT_LIST_FRAG = "contactListFrag";
    private static final String DETAIL_FRAGMENT = "detail";
    private static final int CONTACT_DETAIL_REQUEST = 100;
    private static final int ADD_CONTACT = 200;


    public static ContactsFragment newInstance() {
        return new ContactsFragment();
    }


    public ContactsFragment() {
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        haveDynamicFragment = view.findViewById(R.id.contact_detail_container) != null;

        if (savedInstanceState == null) {
            ContactListFragment contactList =
                    ContactListFragment.newInstance(getString(R.string.title_contact_list));
            getChildFragmentManager()
                    .beginTransaction()
                    .add(R.id.contactListFragmentContainer, contactList, CONTACT_LIST_FRAG)
                    .commit();
        }

        if (haveDynamicFragment) {
            ContactDetailFragment contactDetail = (ContactDetailFragment)
                    getChildFragmentManager().findFragmentByTag(DETAIL_FRAGMENT);
            if (contactDetail == null) {
                contactDetail = ContactDetailFragment.newInstance(DummyContent.ITEMS.get(0));
                getChildFragmentManager()
                        .beginTransaction()
                        .add(R.id.contact_detail_container, contactDetail, DETAIL_FRAGMENT)
                        .commit();
            }
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_contact, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id) {
            case R.id.addContact:
                doAddContact();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Handle result from detail activity
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ADD_CONTACT) {
                //update the fragment -- this isn't working... :-(
                ContactListFragment contactList =
                        (ContactListFragment) getChildFragmentManager().findFragmentByTag(CONTACT_LIST_FRAG);
                contactList.getAdapter().notifyDataSetChanged();
                return;
            }
        }
        // Didn't handle, so let parent have shot
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void doAddContact() {
        Intent intent = AddContactActivity.buildIntent(getActivity());
        startActivityForResult(intent, ADD_CONTACT);
    }


    @Override
    public void onUpdateContactDetail(DummyContent.DummyItem item) {
        if (haveDynamicFragment) {
            ContactDetailFragment fragment =
                    ContactDetailFragment.newInstance(item);
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contact_detail_container, fragment, DETAIL_FRAGMENT)
                    .commit();
        }
    }


    @Override
    public void onContactItemClick(DummyContent.DummyItem item) {
        if (haveDynamicFragment) {
            ContactDetailFragment detailFragment = ContactDetailFragment.newInstance(item);
            getChildFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contact_detail_container, detailFragment, DETAIL_FRAGMENT)
                    .commit();
        } else {
            Intent intent = ContactDetailActivity.buildIntent(getActivity(), item);
            startActivityForResult(intent, CONTACT_DETAIL_REQUEST);
        }
    }
}
