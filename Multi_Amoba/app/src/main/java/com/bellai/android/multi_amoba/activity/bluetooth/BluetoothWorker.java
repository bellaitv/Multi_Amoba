package com.bellai.android.multi_amoba.activity.bluetooth;

import android.app.Activity;
import android.bluetooth.*;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.bellai.android.multi_amoba.R;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

/**
 * Created by adam.bellai on 2017. 01. 13..
 */

public class BluetoothWorker {

    /*
    * doggie address 02:00:00:00:00:00
    *
    * */

    public static final String UUID_STRING = "0000111f-0000-1000-8000-00805f9b34fb";//"0000111f-0000-1000-8000-00805f9b34fb";
    public static final String NAME = "76:36:46:03:35:80";//;

    private static final String TAG = BluetoothWorker.class.getName();

    private static final String BLUETOOTH_PROBLEM_DIALOG_TITLE =
            "Something is wrong with your bluetooth connection";
    private static final String BLUETOOTH_IS_NOT_ENABLED = "Bluetooth is not enabled.";
    private static final String DO_YOU_WANT_TO_TURN_ON = "Do you want to turn on?";

    private BluetoothAdapter bluetooth;
    private InputStream mInputStream;
    private OutputStream mOutputStream;

    public BluetoothWorker() {
        bluetooth = BluetoothAdapter.getDefaultAdapter();
    }

    public void accept() {
        Log.d(TAG, "accept start");
        UUID uuid = UUID.fromString(UUID_STRING);
        try {
            BluetoothServerSocket serverSocket = BluetoothAdapter.getDefaultAdapter().
                    listenUsingRfcommWithServiceRecord(NAME, uuid);
            BluetoothSocket socket = serverSocket.accept(30000);
            mOutputStream = socket.getOutputStream();
            mInputStream = socket.getInputStream();
            Log.d(TAG, "socket accepted");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkBluetooth(Activity activity) {
        BluetoothDialogMaker dialogMaker;
        if (bluetooth == null) {
            dialogMaker = new BluetoothDialogMaker(
                    activity, BLUETOOTH_PROBLEM_DIALOG_TITLE, BLUETOOTH_PROBLEM_DIALOG_TITLE,
                    bluetooth);
            dialogMaker.initialize();
            dialogMaker.show();
        }
        return bluetooth.isEnabled();
    }

    public void showEnableBluetoothDialog(final Activity activity, boolean isServer) {
        BluetoothDialogMaker dialogMaker;
        dialogMaker = new BluetoothDialogMaker(
                activity, BLUETOOTH_IS_NOT_ENABLED, DO_YOU_WANT_TO_TURN_ON, bluetooth);
        dialogMaker.initializeToBlouTooth(this, isServer);
        dialogMaker.show();
    }

    public void enableWifi() {
        bluetooth.enable();
    }

    public void connect(String deviceName) {
        BluetoothSocket socket = null;
        try {
            asd();
            BluetoothDevice device = bluetooth.getRemoteDevice(NAME);
            if (device != null) {
                UUID uuid = UUID.fromString(UUID_STRING);
                socket = device.createRfcommSocketToServiceRecord(uuid);
                socket.connect();
                mOutputStream = socket.getOutputStream();
                mInputStream = socket.getInputStream();
                Log.d(TAG, "connected");
            /*boolean connectionIsSuccessed = gatt.connect();
            Log.d(TAG, String.format("conection success: %b", connectionIsSuccessed));*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write(String s) throws IOException {
        mOutputStream.write(s.getBytes());
    }

    public void run() {
        final int BUFFER_SIZE = 1024;
        byte[] buffer = new byte[BUFFER_SIZE];
        int bytes = 0;
        int b = BUFFER_SIZE;

        while (true) {
            try {
                bytes = mInputStream.read(buffer, bytes, BUFFER_SIZE - bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public OutputStream getOutputStream() {
        return mOutputStream;
    }

    public void asd() {
        try {
            BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

            Method getUuidsMethod = BluetoothAdapter.class.getDeclaredMethod("getUuids", null);

            ParcelUuid[] uuids = (ParcelUuid[]) getUuidsMethod.invoke(adapter, null);

            for (ParcelUuid uuid : uuids) {
                Log.d(TAG, "UUID: " + uuid.getUuid().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
