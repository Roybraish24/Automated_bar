package com.example.automatedbar;

import static java.security.AccessController.getContext;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;

import android.os.Handler;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.widget.ConstraintSet;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SwipeButton {

    RelativeLayout parentLayout;
    Context context;

    MotionLayout motionLayout;
    TextView SwipeText;
    String message;
    AppCompatImageView SwipeIcon;
    float percentage;
//    TextView shadow;
    Handler handler;
    helperClass helper;
    BluetoothService btService;
    @SuppressLint("UseCompatLoadingForDrawables")
    public SwipeButton(RelativeLayout parentlayout,helperClass Helper) {
        parentLayout=parentlayout;
        context=parentLayout.getContext();
        SwipeText= parentLayout.findViewById(R.id.tv_lable);
        motionLayout = parentLayout.findViewById(R.id.lay_lock_main);
        SwipeIcon=parentLayout.findViewById(R.id.im_lock);
//        shadow = parentLayout.findViewById(R.id.tv_shadow);
        helper=Helper;
        btService=BluetoothService.getInstance(context);


    }

    static boolean isTouchOutsideInitialPosition(MotionEvent event, View view) {
        return event.getX() > view.getX() + view.getWidth();
    }

    public void EnterMessage(String Message){
        message=Message;
    }

    public void ConnectMotionLayout() {
        motionLayout.setTransitionListener(new MotionLayout.TransitionListener() {
            @Override
            public void onTransitionTrigger(MotionLayout p0, int p1, boolean p2, float p3) {
                SwipeIcon.setImageResource(R.drawable.swipe_drink_icon);

            }

            @Override
            public void onTransitionStarted(MotionLayout p0, int p1, int p2) {
                SwipeIcon.setImageResource(R.drawable.swipe_drink_icon);

            }

            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onTransitionChange(MotionLayout p0, int p1, int p2, float p3) {
                int[] original={ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
                                21,22,23,24,25,26,27,28,29,30,
                                41,42,43,44,45,46,47,48,49,50,
                                61,62,63,64,65,66,67,68,69,70,
                                81,82,83,84,85,86,87,88,89,90};

                int[] flipped={ 11,12,13,14,15,16,17,18,19,20,
                                31,32,33,34,35,36,37,38,39,40,
                                51,52,53,54,55,56,57,58,59,60,
                                71,72,73,74,75,76,77,78,79,80,
                                91,92,93,94,95,96,97,98,99,100};

                percentage=p3*100;

                int intpercentage=(int) percentage;
                float alpha= (float) (0.6-p3);
                SwipeText.setAlpha(alpha);
//                int weight= (int) (315*p3);
//
//                int heightInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
//                int widthInPx = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 135, context.getResources().getDisplayMetrics());
//
//                ViewGroup.LayoutParams params= shadow.getLayoutParams();
//                params.width=widthInPx;
//                shadow.setLayoutParams(params);


                if (Arrays.binarySearch(original,intpercentage)>=0){
                    SwipeIcon.setImageResource(R.drawable.swipe_drink_icon);


                }
                else if (Arrays.binarySearch(flipped,intpercentage)>=0){
                    SwipeIcon.setImageResource(R.drawable.swipe_drink_icon_flipped);



                }








            }

            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onTransitionCompleted(MotionLayout motionLayout, int currentState) {
                /* Check if user completely transitioned to the right */
                if (currentState == motionLayout.getEndState()) {


//                    shadow.setVisibility(View.INVISIBLE);
                    motionLayout.setBackground(parentLayout.getContext().getDrawable(R.drawable.swipe_shape_rounded_swiped));
                    motionLayout.setInteractionEnabled(false);

                    SwipeText.setText("Cheers!!");
                    SwipeText.setAlpha((float) 0.6);
//                    btService.sendMessageToBluetooth("TEST");

                    // MAKE POPUP TO SHOW A DRINK POURING LIKE AN ANIMATION OR SOMEHTING
                    // IN ORDER TO SHOW AS LOADING SCREEN AND LATER RECEIVE MESSAGE DONE FROM ARDUINO TO GO BACK TO ACTIVE DASHBOARD.


//                    SwipeText.setTextAppearance(R.style.Swipe_Text_Style);
                    SwipeText.setGravity(Gravity.CENTER);
                    sendMessage();


                }
            }
        });
    }


    public void sendMessage(){
        String message;
        if (helper.selectedItemText_Portion == null){
            message=helper.selectedItemText_Drink+String.valueOf(0);
        }
        else{
            message=helper.selectedItemText_Drink+helper.selectedItemText_Portion;
        }
        btService.sendMessageToBluetooth(message);


    }
    @SuppressLint("UseCompatLoadingForDrawables")
    public void disable_Swipe(){
        motionLayout.setInteractionEnabled(false);
        SwipeText.setText("Fill Above to order!!");
        motionLayout.setBackground(parentLayout.getResources().getDrawable(R.drawable.swipe_shape_rounded_disabled));
        SwipeIcon.setImageResource(R.drawable.ic_lock_outline_black_24dp);
//        shadow.setBackground(parentLayout.getResources().getDrawable(R.color.black));

    }
    @SuppressLint("UseCompatLoadingForDrawables")
    public void enable_Swipe(){
        changeBackAfterDelay(1000);
        motionLayout.setProgress(0);
        motionLayout.setInteractionEnabled(true);
        SwipeText.setText("Swipe to order!!");
        motionLayout.setBackground(parentLayout.getResources().getDrawable(R.drawable.swipe_shape_rounded_unswiped));
        SwipeIcon.setImageResource(R.drawable.swipe_drink_icon_flipped);
//        shadow.setAlpha(1);
//        shadow.setBackground(parentLayout.getResources().getDrawable(R.color.automated_bar_enabled_red));
    }

    public void changeBackAfterDelay(int delay){
        handler = new Handler();


// start the animation
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                SwipeIcon.setImageResource(R.drawable.ic_lock_open_black_24dp);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Perform some other action after 2 minutes
                        SwipeIcon.setImageResource(R.drawable.swipe_drink_icon_flipped);
                    }
                }, delay);
            }
        };

// start the animation


        handler.post(runnable);

    }


}
