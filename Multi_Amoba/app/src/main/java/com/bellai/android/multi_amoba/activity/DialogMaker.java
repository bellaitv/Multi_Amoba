package com.bellai.android.multi_amoba.activity;

/**
 * Created by adam.bellai on 2016. 12. 30..
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.bellai.android.multi_amoba.R;
import com.bellai.android.multi_amoba.activity.bluetooth.BluetoothWorker;

import java.util.concurrent.CountDownLatch;

/**
 * Created by adam.bellai on 2016. 07. 12..
 */
public class DialogMaker {

    public static final String No = "No";
    public static final String YES = "Yes";

    private Activity activity;
    private AlertDialog.Builder dialogBuilder;
    private String title;
    private String message;

    public DialogMaker(Activity activity, String title, String message) {
        this.activity = activity;
        this.title = title;
        this.message = message;
    }

    public void initialize() {
        this.dialogBuilder = createDialogBuilder();
    }

    public void initializeToBlouTooth(BluetoothWorker worker, boolean isServer) {
        this.dialogBuilder = createDialogBuilder();
        this.dialogBuilder.setPositiveButton(YES, newSingleSelectionListenerToEnableBloetooth(worker, isServer));
        //todo lekezelni a no-t, hogy visszalépjen
    }

    protected AlertDialog.Builder createDialogBuilder() {
        AlertDialog.Builder result = newDialogBuilder();
        result.setIcon(Icon.LAUNCHER);
        result.setTitle(title);
        result.setMessage(message);
        result.setNegativeButton(No, newSingleSelectionListenerToNo());
        result.setPositiveButton(YES, newSingleSelectionListenerToYes());
        return result;
    }

    @NonNull
    protected DialogInterface.OnClickListener newSingleSelectionListenerToNo() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
                dialog.dismiss();
            }
        };
    }

    @NonNull
    protected DialogInterface.OnClickListener newSingleSelectionListenerToYes() {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(activity, CreateNewSocketActivity.class);
                activity.startActivity(intent);
                dialog.dismiss();
            }
        };
    }

    @NonNull
    protected DialogInterface.OnClickListener newSingleSelectionListenerToEnableBloetooth(
            final BluetoothWorker worker, final boolean isServer) {
        return new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                worker.enableWifi();
                if (isServer)
                    worker.accept();
                else
                    worker.connect(BluetoothWorker.NAME);
                dialog.dismiss();
            }
        };
    }

    @NonNull
    protected AlertDialog.Builder newDialogBuilder() {
        return new AlertDialog.Builder(activity);
    }

    public void show() {
        dialogBuilder.show();
    }
}
