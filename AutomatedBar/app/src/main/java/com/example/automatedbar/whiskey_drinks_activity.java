package com.example.automatedbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class whiskey_drinks_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whiskey_drinks);
        Spinner spinner = findViewById(R.id.spinner);

        String[] arrayList= getResources().getStringArray(R.array.whiskey_drinks);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerArrayAdapter);
        spinner.setSelection(0, true);

//
//        Spinner spinner = (Spinner) findViewById(R.id.spinner);
//// Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.whiskey_drinks, android.R.layout.simple_spinner_item);
//// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//// Apply the adapter to the spinner
//        spinner.setAdapter(adapter);

    }

//    public void send_bluetooth_message(View view){
//    }
//
//    public void choose_drink(View view){
//
//    }

}