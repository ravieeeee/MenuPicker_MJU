package com.example.MenuPicker_MJU;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import db.DBHelper;

public class AddMenuActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinner;
    // Create an ArrayAdapter using the string array and a default spinner layout
    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        spinner = (Spinner) findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(this,  R.array.campus, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }

    public void onClick_insert(View v) {
        DBHelper dbHelper = new DBHelper(getApplicationContext(), "MenuBook.db", null, 1);

        EditText etStore = (EditText) findViewById(R.id.store);
        EditText etMenu = (EditText) findViewById(R.id.menu);

        String rawCampus = spinner.getSelectedItem().toString();
        int campus;
        if (rawCampus.equals("인문")) {
            campus = 0;
        } else {
            campus = 1;
        }

        String store = etStore.getText().toString();
        String menu = etMenu.getText().toString();

        dbHelper.insert(campus, store, menu);
        Toast.makeText(getApplicationContext(), "추가 완료!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // Another interface callback

    }
}
