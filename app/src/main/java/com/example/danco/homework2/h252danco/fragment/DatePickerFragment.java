package com.example.danco.homework2.h252danco.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;

import com.example.danco.homework2.h252danco.DummyContent;
import com.example.danco.homework2.h252danco.R;

import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.example.danco.homework2.h252danco.fragment.DatePickerFragment.DatePickerFragmentListener} interface
 * to handle interaction events.
 * Use the {@link DatePickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    private static final String ARG_DATE = DatePickerFragment.class.getName() + ".date";
    private static final String ARG_REQUEST_ID = DatePickerFragment.class.getName() + ".requestId";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private DatePickerFragmentListener listener;
    private int requestId;
    private Date date;


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface DatePickerFragmentListener {
        public void onOkSelected(int requestId, Date date);
        public void onCancelSelected();
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param requestId id.
     * @param date birthdate.
     * @return A new instance of fragment DatePickerFragment.
     */
    public static DatePickerFragment newInstance(int requestId, Date date) {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_REQUEST_ID, requestId);
        args.putLong(ARG_DATE, date.getTime());
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Fragment parent = getParentFragment();
        Object objectToCast = parent != null ? parent : activity;

        try {
            listener = (DatePickerFragmentListener) objectToCast;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.getClass().getSimpleName()
                    + " must implement DatePickerFragmentListener");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            requestId = args.getInt(ARG_REQUEST_ID);
            date = new Date(args.getLong(ARG_DATE));
        }
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Initialize our date picker dialog with the last birthday of the user...
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        DatePickerDialog dialog = new DatePickerDialog(getActivity(), this,
                cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        // No birthdays allowed in the future...
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis());

        // One note. The DatePickerDialog is managing the saved state for us. This is why
        // this fragment isn't trying to do that. It is nice when that happens, but you
        // should always verify expected behavior.
        return dialog;
    }


    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, monthOfYear, dayOfMonth, 0, 0, 0);

        listener.onOkSelected(requestId, cal.getTime());
    }


}
