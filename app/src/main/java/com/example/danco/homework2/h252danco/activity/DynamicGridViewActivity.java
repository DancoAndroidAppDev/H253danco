package com.example.danco.homework2.h252danco.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.danco.homework2.h252danco.R;

public class DynamicGridViewActivity extends ActionBarActivity {
    private static final String TAG = DynamicGridViewActivity.class.getName();

    public static Intent buildIntent(Context context) {
        return new Intent(context, DynamicGridViewActivity.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic_grid_view);
    }
}
