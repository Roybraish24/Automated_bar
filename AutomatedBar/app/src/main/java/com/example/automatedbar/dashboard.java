package com.example.automatedbar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }
    public void gotowhiskey(View view)
    {
        Intent i= new Intent(dashboard.this,whiskey_drinks_activity.class);
        startActivity(i);
    }

}