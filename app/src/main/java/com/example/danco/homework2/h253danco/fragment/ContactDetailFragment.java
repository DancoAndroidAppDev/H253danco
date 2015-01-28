package com.example.danco.homework2.h253danco.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.danco.homework2.h253danco.DummyContent;
import com.example.danco.homework2.h253danco.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;


public class ContactDetailFragment extends Fragment
        implements View.OnClickListener, DatePickerFragment.DatePickerFragmentListener {
    public static final String DATE_PICKER_TAG = "date_picker";

    private static final int BIRTH_REQUEST_CODE = 100;
    public static final String CONTACT = "contact";

    private DummyContent.DummyItem item;

    private final NumberFormat dayMonthFormat = new DecimalFormat("00");

    private ContactDetailFragmentListener listener;

    public interface ContactDetailFragmentListener {
        public void onUpdateContactDetail(DummyContent.DummyItem item);
    }

    public ContactDetailFragment() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param item item for which to display details.
     * @return A new instance of fragment DynamicFragment.
     */
    public static ContactDetailFragment newInstance(DummyContent.DummyItem item) {
        ContactDetailFragment fragment = new ContactDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(CONTACT, item);
        fragment.setArguments(args);
        fragment.item = item;
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            if (getArguments() != null) {
                item = getArguments().getParcelable(CONTACT);
            }
        } else {
            item = savedInstanceState.getParcelable(CONTACT);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_detail, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);

        holder.contactName.setText(item.name);
        holder.contactName.setTypeface(Typeface.DEFAULT_BOLD);
        holder.contactName.setOnClickListener(this);
        holder.streetAddress.setText(item.streetAddress);

        holder.dob.setOnClickListener(this);
    }


    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        // If have saved state, restore our model here.
        if (savedInstanceState != null) {
            item = savedInstanceState.getParcelable(CONTACT);
        }
    }


    @Override
    public void onResume() {
        super.onResume();

        ViewHolder holder = getViewHolder();
        // holder better not be null or there is a bug earlier in the code.
        // onResume always will have a view.

        // Refresh our views. Primarily because we will know our model is restored
        // if we had saved state by this point.
        //
        // Also side benefit is if user changes date format via settings
        // and comes back to app we will honor the new format. Try it...
        updateView(holder);
    }


    @Override
    public void onPause() {
        ViewHolder holder = getViewHolder();
        // holder should not be null as this method is called on UI thread and before
        // destroy view.

        // Update model value for the address so we can save it later in onSaveInstanceState
        item.streetAddress = holder.streetAddress.getText().toString();
        super.onPause();
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(CONTACT, item);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void updateView(ViewHolder holder) {
        if (holder == null) {
            return;
        }

        holder.contactName.setText(item.name);
        holder.streetAddress.setText(item.streetAddress);
        updateBirthDateView(holder.dob, item.dob);
    }


    private void updateBirthDateView(TextView birthDateView, Date newDate) {
        // This is using the user's preferred date format and locale info to
        // format the date. Always best to show date/time in user's preferred format.

        // If also showing time there is a getTimeFormat() method as well
        birthDateView.setText(DateFormat.getDateFormat(getActivity()).format(newDate));
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.contactDob:
                DatePickerFragment fragment = DatePickerFragment.newInstance(BIRTH_REQUEST_CODE, item.dob);
                fragment.show(getChildFragmentManager(), DATE_PICKER_TAG);
                break;
        }
    }


    @Override
    public void onOkSelected(int requestId, @NonNull Date date) {
        if (requestId == BIRTH_REQUEST_CODE) {
            // update our model...
            item.dob = date;

            // update the view as well...
            ViewHolder holder = getViewHolder();
            if (holder != null) {
                updateBirthDateView(holder.dob, item.dob);
            }
        }
    }


    private ViewHolder getViewHolder() {
        View view = getView();
        // getView will return null after onDestroyView
        return view != null ? (ViewHolder) view.getTag() : null;
    }


    @Override
    public void onCancelSelected() {

    }


    static class ViewHolder {
        final TextView contactName;
        final EditText streetAddress;
        final TextView dob;


        ViewHolder(View view) {
            contactName = (TextView) view.findViewById(R.id.contact_name);
            streetAddress = (EditText) view.findViewById(R.id.contact_street_address);
            dob = (TextView) view.findViewById(R.id.contactDob);
        }
    }
}
