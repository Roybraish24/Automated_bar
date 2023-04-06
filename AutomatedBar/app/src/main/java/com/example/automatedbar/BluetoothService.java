package com.example.automatedbar;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothService {
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String ARDUINO_MAC_ADDRESS = "FC:A8:9A:00:1E:56";
    BluetoothDevice arduinoDevice;
    BluetoothSocket arduinoSocket;
    private BroadcastReceiver mReceiver;

    String returned_message;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothSocket mBluetoothSocket;
    private static BluetoothService instance = null;
    private Context context;


    private BluetoothService(Context context) {
        this.context = context;


    }

    public static BluetoothService getInstance(Context context) {
        if (instance == null) {
            instance = new BluetoothService(context);
            instance.onCreate();
        }
        return instance;
    }


    public void onCreate() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            // Do nothing
        } else {

            // Register a broadcast receiver to listen to Bluetooth state changes
            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    final String action = intent.getAction();
                    if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
                        // Bluetooth connection has been disconnected
                        // Handle the disconnection event here
                        connectToBluetooth();
                    }
                    if (action != null && action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                        final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                        switch (state) {
                            case BluetoothAdapter.STATE_OFF:
                                // Bluetooth is off
                                break;
                            case BluetoothAdapter.STATE_TURNING_OFF:
                                // Bluetooth is turning off
                                break;
                            case BluetoothAdapter.STATE_ON:
                                // Bluetooth is on
                                break;
                            case BluetoothAdapter.STATE_TURNING_ON:
                                // Bluetooth is turning on
                                break;
                        }
                    }
                }
            };

            IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);

        }

        checkBluetooth();
        connectToBluetooth();

    }



    @SuppressLint("MissingPermission")
    private void checkBluetooth() {
        // Check if Bluetooth is on
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            // Do nothing
        } else {
            if (!mBluetoothAdapter.isEnabled()) {
                // Bluetooth is not enabled, ask the user to turn it on
//                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                enableBtIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(enableBtIntent);
//                finish();
            }
        }
    }
//
//    private void startActivityForResult(Intent enableBtIntent, int request_enable_bt) {
//    }

    private void finish() {finish();
    }



    @SuppressLint("MissingPermission")
    public void connectToBluetooth() {
        arduinoDevice = mBluetoothAdapter.getRemoteDevice(ARDUINO_MAC_ADDRESS);

        //Creating a BluetoothSocket to connect with the device
        try {

            arduinoSocket = arduinoDevice.createRfcommSocketToServiceRecord(MY_UUID);
            arduinoSocket.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String wait_for_order() throws IOException {
        return wait_for_order(10000);
    }

    public String wait_for_order(int timeout) throws IOException {
        InputStream inputStream= arduinoSocket.getInputStream();
        long startTime = System.currentTimeMillis();

        while (System.currentTimeMillis() - startTime < timeout) {
            if (inputStream.available() > 0) {
                // read the available data
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                String message = new String(buffer, "UTF-8");
                Log.d("Bluetooth", "Received message: " + message);
                return message;
            }
        }

        if (System.currentTimeMillis() - startTime >= timeout) {
            // timeout occurred, no data received
            Log.d("Bluetooth", "Timeout occurred, no data received");
        }

        return "null";
    }

    public boolean is_confirmed_connected()
    {
        sendMessageToBluetooth("TESTCONNECTION");
        return false;
    }
    public String sendMessageToBluetooth(String message) {
        try {
            OutputStream arduinoOutputStream = arduinoSocket.getOutputStream();
            arduinoOutputStream.write(message.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            returned_message=wait_for_order();
        }  catch (IOException ignored) {
                ;
        }
//        if (returned_message == "null"){
//            pop_up("Bar not connected","Make sure your bar is connected and try again...");
//        }
        return returned_message;


//        loading_screen loadingScreen = new loading_screen();
//        loadingScreen.show(getSupportFragmentManager(), "loading_screen");




    }



}
