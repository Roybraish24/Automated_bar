package com.example.automatedbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class dashboard extends AppCompatActivity {
    private Intent intent;
    BluetoothService btService;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        RelativeLayout parentLayout= (RelativeLayout) findViewById(R.id.Parent_relative_layout);

        btService = BluetoothService.getInstance(dashboard.this);

//        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

    }
    public void go_to_whiskey(View view)
    {
        Intent i= new Intent(dashboard.this,whiskey_drinks_activity.class);
        startActivity(i);
    }

    public void go_to_gin(View view)
    {
//        Intent i= new Intent(dashboard.this,test.class);
//        startActivity(i);
        ApiHelper apiHelper = new ApiHelper(this,"Password123");
        apiHelper.get_data(response -> {
            // Handle successful response
            Toast.makeText(dashboard.this, "Response: " + response, Toast.LENGTH_SHORT).show();
            Log.d("RESPONSE SUCCESS", "Response: " + response);

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error response
                Toast.makeText(dashboard.this, "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
                Log.d("RESPONSE FAIL", "Error: " + error.toString());

            }
        });

//        apiHelper.("John", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                // Handle successful response
//                Toast.makeText(getActivity(), "Response: " + response, Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // Handle error response
//                Toast.makeText(getActivity(), "Error: " + error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });


    }
    public void bluetoothTest(View view){
//        btService.sendMessageToBluetooth("VODKA");
        AlertDialog.Builder builder = new AlertDialog.Builder(dashboard.this);
        builder.setTitle("Title");
        builder.setMessage("Message");
        builder.setPositiveButton("OK", (dialog, which) -> {
            // Do something when the OK button is clicked
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            // Do something when the Cancel button is clicked
        });
        AlertDialog dialog = builder.create();
        dialog.show();



    }
}