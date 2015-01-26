package com.example.danco.homework2.h252danco.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import com.example.danco.homework2.h252danco.R;
import com.example.danco.homework2.h252danco.adapter.GridAdapter;

import java.util.ArrayList;

/**
 * Created by danco on 1/17/15.
 */
public class DynamicGridViewFragment extends Fragment {

    private static final int CELL_COUNT = 40;

    private static final ArrayList<String> data = new ArrayList<>(CELL_COUNT);
    static {
        for (int i = 0; i < CELL_COUNT; i++) {
            data.add("Cell Text " + i);
        }
    }


    public static DynamicGridViewFragment newInstance() {
        return new DynamicGridViewFragment();
    }

    public DynamicGridViewFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dynamic_gridview, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        GridView grid = (GridView) view.findViewById(R.id.gridView);
        GridAdapter adapter = new GridAdapter(getActivity(), data);
        grid.setAdapter(adapter);
    }
}
