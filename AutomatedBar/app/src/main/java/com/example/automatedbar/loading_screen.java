package com.example.automatedbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.io.IOException;

public class loading_screen extends DialogFragment {

    private ProgressBar mProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_loading_screen, container, false);
        mProgressBar = view.findViewById(R.id.progressBar);
        getDialog().setCanceledOnTouchOutside(false);
        
        return view;

    }

    public void setProgress(int progress) {
        mProgressBar.setProgress(progress);
    }
}
//
//public class loading_screen extends AppCompatActivity {
//    private ProgressDialog mLoadingDialog;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_loading_screen);
//        BluetoothService btService= BluetoothService.getInstance(this);
//
//
//        mLoadingDialog = new ProgressDialog(this);
//        mLoadingDialog.setMessage("Loading...");
//        mLoadingDialog.setCancelable(false);
//        mLoadingDialog.show();
//        try {
//            btService.wait_for_order();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        hideLoadingDialog();
//        // Start your long-running task here
//
//
//    }
//
//    // hide the loading dialog when the task is finished
//    private void hideLoadingDialog() {
//        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
//            mLoadingDialog.dismiss();
//        }
//    }
//}