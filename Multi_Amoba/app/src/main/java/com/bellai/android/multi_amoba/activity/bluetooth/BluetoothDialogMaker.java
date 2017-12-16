package com.bellai.android.multi_amoba.activity.bluetooth;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;

import com.bellai.android.multi_amoba.activity.DialogMaker;

/**
 * Created by adam.bellai on 2017. 01. 13..
 */

public class BluetoothDialogMaker extends DialogMaker {

    private BluetoothAdapter mBluetoothAdapter;

    public BluetoothDialogMaker(Activity activity, String title, String message) {
        super(activity, title, message);
    }

    public BluetoothDialogMaker(Activity activity, String title, String message, BluetoothAdapter mBluetoothAdapter) {
        super(activity, title, message);
        this.mBluetoothAdapter = mBluetoothAdapter;
    }

    @Override
    protected DialogInterface.OnClickListener newSingleSelectionListenerToNo() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        };
    }

    @Override
    protected DialogInterface.OnClickListener newSingleSelectionListenerToYes() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mBluetoothAdapter.enable();
            }
        };
    }
}
