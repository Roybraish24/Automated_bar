package com.example.automatedbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.os.Bundle;

import android.widget.RelativeLayout;
import android.widget.TextView;


public class test extends AppCompatActivity {

    SwipeButton Swipe_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        RelativeLayout parentLayout=findViewById(R.id.Parent_relative_layout);
        TextView SwipeText= findViewById(R.id.tv_lable);
        MotionLayout motionLayout= findViewById(R.id.lay_lock_main);
//        SwipeButton Swipe_Button= new SwipeButton(findViewById(R.id.Parent_relative_layout));
//        Swipe_Button.ConnectMotionLayout();

    }
}