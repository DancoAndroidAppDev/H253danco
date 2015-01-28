package com.example.danco.homework2.h252danco.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.danco.homework2.h252danco.DummyContent;
import com.example.danco.homework2.h252danco.R;


/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 */
public class ContactListFragment extends Fragment
        implements AbsListView.OnItemClickListener,
        ContactDetailFragment.ContactDetailFragmentListener {

    private static final String EXTRA_VALUES =
            ContactListFragment.class.getSimpleName() + ".values";
    private static final String EXTRA_TEXT = ContactListFragment.class.getSimpleName() + ".text";
    private static final String EXTRA_SELECTION =
            ContactListFragment.class.getSimpleName() + ".selection";
    private static final String EXTRA_TITLE = ContactListFragment.class.getSimpleName() + ".title";
    private static final String ARG_TITLE = EXTRA_TITLE;

    private static final String CONTACT_LIST_FRAG = "contactListFrag";
    private static final String DETAIL_FRAGMENT = "detail";

    private int selectedItem = 0;

    private boolean haveDynamicFragment = false;

    private ItemFragmentListener mListener;

    public interface ItemFragmentListener {
        public void onUpdateDynamicFragment(DummyContent.DummyItem item);
    }


    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;


    /**
     *
     */
    public static ContactListFragment newInstance(final String title) {
        ContactListFragment fragment = new ContactListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ContactListFragment() {
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_contact_list, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);

        mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1,
                DummyContent.ITEMS);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        mListView.setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        mListView.setSelector(R.drawable.selected_state_selector);
        mListView.setItemChecked(selectedItem, true);

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onUpdateDynamicFragment(DummyContent.ITEMS.get(position));
            mListView.setItemChecked(position, true);
        }
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
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            // restore the saved contact details
            String savedText = savedInstanceState.getString(EXTRA_TEXT);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // TODO save the contact details
    }


    private ViewHolder getViewHolder() {
        View view = getView();
        // return view holder, if the view is not null
        return view != null ? (ViewHolder) view.getTag() : null;
    }


    static class ViewHolder {
        final TextView textView;

        ViewHolder(View view) {
            textView = (TextView) view.findViewById(R.id.list_item);
        }
    }
}
