package com.example.danco.homework2.h253danco.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.danco.homework2.h253danco.DummyContent;
import com.example.danco.homework2.h253danco.R;

public class AddContactActivity extends ActionBarActivity implements View.OnClickListener {
    private static final String TAG = AddContactActivity.class.getSimpleName();
    private static final String CONTACT_LIST_FRAG = "contactListFrag";

    private EditText contactName;
    private EditText streetAddress;
    private EditText city;
    private EditText state;
    private EditText zip;


    public static Intent buildIntent(Context context) {
        Intent intent = new Intent(context, AddContactActivity.class);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar_add_contact));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        contactName = (EditText) findViewById(R.id.contact_name);
        streetAddress = (EditText) findViewById(R.id.contact_street_address);
        city = (EditText) findViewById(R.id.contact_city);
        state = (EditText) findViewById(R.id.contact_state);
        zip = (EditText) findViewById(R.id.contact_zip);

        Button prefsBtn = (Button)findViewById(R.id.button_add_contact);
        prefsBtn.setOnClickListener(this);
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
    public void onClick(View v) {
        if (isEmpty(contactName.getText())) {
            Toast.makeText(this, "Name can't be empty", Toast.LENGTH_LONG).show();
        } else if (isEmpty(streetAddress.getText())) {
            Toast.makeText(this, "Address can't be empty", Toast.LENGTH_LONG).show();
        } else if (isEmpty(city.getText())) {
            Toast.makeText(this, "City can't be empty", Toast.LENGTH_LONG).show();
        } else if (isEmpty(state.getText())) {
            Toast.makeText(this, "State can't be empty", Toast.LENGTH_LONG).show();
        } else if (isEmpty(zip.getText())) {
            Toast.makeText(this, "Zip code can't be empty", Toast.LENGTH_LONG).show();
        } else {
            DummyContent.DummyItem item =
                    new DummyContent.DummyItem(Integer.toString(DummyContent.ITEMS.size()),
                            contactName.getText().toString());
            item.streetAddress = streetAddress.getText().toString();
            item.city = city.getText().toString();
            item.state = state.getText().toString();
            item.zip = zip.getText().toString();

            DummyContent.addItem(item);
            setResult(RESULT_OK);
            finish();
        }
    }

    public boolean isEmpty(Editable editable){

        if (editable.equals(null)) {
            Log.i(TAG, "isEmpty - editable is null");

            return true;
        }
        else {
            return editable.toString().trim().length() == 0;
        }
    }

}
