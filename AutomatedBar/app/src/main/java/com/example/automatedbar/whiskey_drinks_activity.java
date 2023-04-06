package com.example.automatedbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class whiskey_drinks_activity extends AppCompatActivity {
    RelativeLayout parentLayout;
    SwipeButton Swipe_button;
    helperClass helper;
    BluetoothService btService;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whiskey_drinks);
        parentLayout= (RelativeLayout) findViewById(R.id.Parent_relative_layout);

        helper = new helperClass(parentLayout,this);
        Swipe_button=new SwipeButton(parentLayout,helper);
        Swipe_button.ConnectMotionLayout();
        Swipe_button.disable_Swipe();


        String[] arrayList = getResources().getStringArray(R.array.whiskey_drinks);
        helper.CreateSpinner_SelectDrink(arrayList);
        RequestQueue queue = Volley.newRequestQueue(this);

    }


    public void back_to_dashboard(View view) {
        finish();
    }



}