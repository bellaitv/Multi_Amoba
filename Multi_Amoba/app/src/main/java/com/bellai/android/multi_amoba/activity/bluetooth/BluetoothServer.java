package com.bellai.android.multi_amoba.activity.bluetooth;

import android.app.Activity;

import com.bellai.android.multi_amoba.activity.CommunicationInterface.IServerCommunication;
import com.bellai.android.multi_amoba.activity.NewServerGameActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adam.bellai on 2017. 01. 22..
 */

public class BluetoothServer implements IServerCommunication {

    private BluetoothWorker mBluetoothWorker;

    public BluetoothServer(Activity activity) {
        mBluetoothWorker = new BluetoothWorker();
        boolean enabled = mBluetoothWorker.checkBluetooth(activity);
        if (!enabled)
            mBluetoothWorker.showEnableBluetoothDialog(activity, true);
        else
            mBluetoothWorker.accept();
    }

    @Override
    public List<Object> getOutStreams() {
        List<Object> result = new ArrayList<>();
        result.add(mBluetoothWorker.getOutputStream());
        return result;
    }

    @Override
    public void setNewServerGameActivity(NewServerGameActivity newServerGameActivity) {

    }
}
