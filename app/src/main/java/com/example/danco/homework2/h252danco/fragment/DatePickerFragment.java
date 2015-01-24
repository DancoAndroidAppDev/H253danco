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
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String BIRTH_YEAR = "year";
    private static final String BIRTH_MONTH = "month";
    private static final String BIRTH_DAY_OF_MONTH = "day";

    private int year;
    private int month;
    private int day;

    private DatePickerFragmentListener listener;


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
        public void onOkSelected(int year, int month, int dayOfMonth);
        public void onCancelSelected();
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param year birth year1.
     * @param month birth month.
     * @param day birth day of month.
     * @return A new instance of fragment DatePickerFragment.
     */
    public static DatePickerFragment newInstance(int year, int month, int day) {
        DatePickerFragment fragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putInt(BIRTH_YEAR, year);
        args.putInt(BIRTH_MONTH, month);
        args.putInt(BIRTH_DAY_OF_MONTH, day);
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


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (savedInstanceState == null) {
            year = 2015;
            month = 1;
            day = 1;
        } else {
            DummyContent.DummyItem item =
                    savedInstanceState.getParcelable(getResources().getString(R.string.contact));
            year = item.birthYear;
            month = item.birthMonth;
            day = item.birthDayOfMonth;
        }

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }


    @Override
    public void onDetach() {
        listener = null;
        super.onDetach();
    }


    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year = year;
        this.month = monthOfYear;
        this.day = dayOfMonth;

        listener.onOkSelected(year, monthOfYear, dayOfMonth);
    }


}
