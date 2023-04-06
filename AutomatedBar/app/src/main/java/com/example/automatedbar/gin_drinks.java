package com.example.automatedbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class gin_drinks extends AppCompatActivity {

    RelativeLayout parentLayout;
    helperClass helper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gin_drinks);
        parentLayout= (RelativeLayout) findViewById(R.id.Parent_relative_layout);

        helper = new helperClass(parentLayout,gin_drinks.this);

//        Button Done=(Button)findViewById(R.id.OrderButton);
//        Done.setEnabled(false);

        Spinner spinner = findViewById(R.id.spinnerSelectDrink);
        String[] arrayList = getResources().getStringArray(R.array.whiskey_drinks);

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList) {
            @Override
            public boolean isEnabled(int position) {
                // Disable the first item from Spinner
                // First item will be use for hint
                return position != 0;
            }

            @Override
            public View getDropDownView(
                    int position, View convertView,
                    @NonNull ViewGroup parent) {

                // Get the item view
                View view = super.getDropDownView(
                        position, convertView, parent);
                TextView textView = (TextView) view;
                if (position == 0) {
                    // Set the hint text color gray
                    textView.setTextColor(Color.GRAY);
                } else {
                    textView.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
//
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent, View view,
                            int position, long id) {

                        // Get the spinner selected item text
                        String selectedItemText = (String) parent
                                .getItemAtPosition(position);
                        TextView spinnerText = (TextView) spinner.getSelectedView();
                        spinnerText.setTypeface(spinnerText.getTypeface(), Typeface.BOLD);
                        getDrawable(R.drawable.spinner_background);
                        if (selectedItemText.contains("cola") ){
                            // Assume this is the parent layout where you want to add the TextView
                            try{TextView text= findViewById(R.id.portionTextview);
                                text.setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                Create_portion_textview();
                            }
                            try{Spinner spinner= findViewById(R.id.portionSpinner);
                                spinner.setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                Create_portion_spinner();
                            }
                            enable_order(false);
                        } else if (selectedItemText.contains("Select")) {
                            enable_order(false);
                        }
                        else if (selectedItemText.equals("whiskey")){
                            try {TextView text= findViewById(R.id.portionTextview);
                                text.setVisibility(View.GONE);
                            } catch (Exception e) { ;}

                            try {Spinner spinner= findViewById(R.id.portionSpinner);
                                spinner.setVisibility(View.GONE);
                            } catch (Exception e) { ;}

                            enable_order(true);
                        }

                        // If user change the default selection
                        // First item is disable and
                        // it is used for hint
                    }

                    @Override
                    public void onNothingSelected(
                            AdapterView<?> parent) {
                    }
                });


        // Finally, data bind the spinner object with adapter
        spinner.setAdapter(spinnerArrayAdapter);
    }


    public void back_to_dashboard(View view) {
        finish();
    }


    public void orderNow(View view) {
        helper.order_now();
    }

    public void Create_portion_textview() {
        helper.CreateTextView( R.id.SecondSpace);
    }

    public void Create_portion_spinner(){
        helper.createSpinner();

    }

    public void enable_order(boolean state){
        helper.enable_order(state);
    }
}