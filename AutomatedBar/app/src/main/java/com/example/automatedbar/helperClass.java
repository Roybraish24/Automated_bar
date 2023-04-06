package com.example.automatedbar;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class helperClass
{

    String selectedItemText_Drink,selectedItemText_Portion=null;
    private RelativeLayout parentLayout;
    private Context context;
    SwipeButton Swipe_button;
    BluetoothService btService;


    public helperClass(RelativeLayout parentLayout,Context context1){

        this.parentLayout=parentLayout;
        context=context1;
        Swipe_button=new SwipeButton(parentLayout,this);
        btService = BluetoothService.getInstance(context1);


    }


    public  void CreateTextView( int id){
//        parentLayout = findViewById(R.id.Parent_relative_layout);

        TextView textView = new TextView(parentLayout.getContext());
        textView.setId(R.id.portionTextview);
        textView.setText(R.string.choose_portion);
        textView.setTextAppearance(R.style.SelectDrink_PortionLabel_style);
        int heightInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, context.getResources().getDisplayMetrics());

        RelativeLayout.LayoutParams params2= new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, heightInPx);
        int x = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, context.getResources().getDisplayMetrics());

        params2.setMargins(x,0,0,0);
        params2.addRule(RelativeLayout.BELOW, id);
        textView.setLayoutParams(params2);
        textView.setGravity(Gravity.BOTTOM);
        parentLayout.addView(textView);

    }

    public void CreateSpinner_SelectDrink(String[] arrayList){
        Spinner spinner = parentLayout.findViewById(R.id.spinnerSelectDrink);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayList) {
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

                    @SuppressLint("UseCompatLoadingForDrawables")
                    @Override
                    public void onItemSelected(
                            AdapterView<?> parent, View view,
                            int position, long id) {

                        // Get the spinner selected item text
                        selectedItemText_Drink = (String) parent
                                .getItemAtPosition(position);
                        TextView spinnerText = (TextView) spinner.getSelectedView();
                        spinnerText.setTypeface(spinnerText.getTypeface(), Typeface.BOLD);
                        parentLayout.getResources().getDrawable(R.drawable.spinner_background);
                        if (selectedItemText_Drink.contains("cola") ){
                            // Assume this is the parent layout where you want to add the TextView
                            try{TextView text= parentLayout.findViewById(R.id.portionTextview);
                                text.setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                CreateTextView( R.id.SecondSpace);
                            }
                            try{Spinner spinner= parentLayout.findViewById(R.id.portionSpinner);
                                spinner.setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                createSpinner();
                            }
                            enable_order(false);
                        } else if (selectedItemText_Drink.contains("Select")) {
                            enable_order(false);
                        }
                        else if (selectedItemText_Drink.equals("whiskey")){
                            try {TextView text= parentLayout.findViewById(R.id.portionTextview);
                                text.setVisibility(View.GONE);
                            } catch (Exception e) { }

                            try {Spinner spinner= parentLayout.findViewById(R.id.portionSpinner);
                                spinner.setVisibility(View.GONE);
                                selectedItemText_Portion=null;
                            } catch (Exception e) { }

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

    public void get_selected_SpinnerSelectDrink(){

    }
    @SuppressLint("UseCompatLoadingForDrawables")
    public void createSpinner() {
        context=parentLayout.getContext();

        Spinner spinner = new Spinner(parentLayout.getContext());
        String[] arrayList = context.getResources().getStringArray(R.array.portion_list);

        @SuppressLint("ResourceType") ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, arrayList) {
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
                        selectedItemText_Portion = (String) parent
                                .getItemAtPosition(position);
                        TextView spinnerText = (TextView) spinner.getSelectedView();
                        spinnerText.setTypeface(spinnerText.getTypeface(), Typeface.BOLD);

                        enable_order(!selectedItemText_Portion.contains("Select"));


                    }

                    @Override
                    public void onNothingSelected(
                            AdapterView<?> parent) {
                    }
                });


//        Context context=parentLayout.getContext();

        spinner.setId(R.id.portionSpinner);
//        spinner.setAdapter(ArrayAdapter.createFromResource(this, R.array.portion_list, android.R.layout.simple_spinner_item));
        spinner.setAdapter(spinnerArrayAdapter);
        int height_spinner = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context.getResources().getDisplayMetrics());

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height_spinner);

        params.addRule(RelativeLayout.BELOW, R.id.portionTextview);
        spinner.setBackground(context.getResources().getDrawable(R.drawable.spinner_background));
        int heightInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, context.getResources().getDisplayMetrics());
        int x = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, context.getResources().getDisplayMetrics());

        params.setMargins(x,heightInPx,x,0);

        spinner.setLayoutParams(params);
        parentLayout.addView(spinner);

    }

    @SuppressLint("ResourceAsColor")
    public void enable_order(boolean state){
//        int s=1;
        if (state){Swipe_button.enable_Swipe();}
        else {
            Swipe_button.disable_Swipe();
        }

//        Button Done= parentLayout.findViewById(R.id.OrderButton);
//        int originalColor = Color.parseColor("#424146");
//        float[] hsv = new float[3];
//        Color.colorToHSV(originalColor, hsv);
//        hsv[2] *= 0.1f; // value component
//        int disabledColor = Color.HSVToColor(hsv);
//        if (state){
//            Done.setAlpha(1F);
//        }
//        else {
//            Done.setAlpha(0.5F);
////            Done.setBackgroundColor(disabledColor);
//        }
//        Done.setEnabled(state);
    }

    public String order_now(){

        String message;
        if (selectedItemText_Portion == null){
            message=selectedItemText_Drink+String.valueOf(0);
        }
        else{
            message=selectedItemText_Drink+selectedItemText_Portion;
        }
        return btService.sendMessageToBluetooth(message);

    }

}


