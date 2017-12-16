package com.bellai.android.multi_amoba.activity.bluetooth;

import android.app.Activity;

import com.bellai.android.multi_amoba.activity.CommunicationInterface.IClientCommunication;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * Created by adam.bellai on 2017. 01. 22..
 */

public class BluetoothClient implements IClientCommunication {

    private BluetoothWorker mBluetoothWorker;

    public BluetoothClient(Activity activity) {
        mBluetoothWorker = new BluetoothWorker();
        boolean enabled = mBluetoothWorker.checkBluetooth(activity);
        if (!enabled)
            mBluetoothWorker.showEnableBluetoothDialog(activity, false);
        else
            mBluetoothWorker.connect(BluetoothWorker.NAME);
        //mBluetoothWorker.connect("02:00:00:00:00:00");
    }

    @Override
    public void sendMessage(String msg) {
        try {
            mBluetoothWorker.write(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
