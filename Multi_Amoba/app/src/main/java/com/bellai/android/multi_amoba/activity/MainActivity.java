package com.bellai.android.multi_amoba.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bellai.android.multi_amoba.R;

public class MainActivity extends BaseActivity {

    public final static String MODE = "MODE";
    public final static int WIFI_MODE = 1;
    public final static int BLUETOOTH_MODE = 2;

    private TextView mtextViewWifiGame;
    //private TextView mtextViewBluetoothGame;
    private TextView mtextViewQuit;
    private Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        initialize();
    }

    @Override
    public void initialize() {
        mtextViewWifiGame = (TextView) findViewById(R.id.textViewWifiGame);
        //mtextViewBluetoothGame = (TextView) findViewById(R.id.textViewBluetoothGame);
        mtextViewQuit = (TextView) findViewById(R.id.textViewQuit);
        mtextViewWifiGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewActivity(GameActivity.class, WIFI_MODE);
            }
        });
        /*mtextViewBluetoothGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo navigate to bluetooth game activity
                goToNewActivity(GameActivity.class, BLUETOOTH_MODE);
                *//*BluetoothWorker bluetoothWorker = new BluetoothWorker();
                bluetoothWorker.checkBluetooth(mActivity);
                bluetoothWorker.connect("02:00:00:00:00:00", mActivity);*//*
                //goToNewActivity(ConnectToSocketActivity.class);
            }
        });*/
        mtextViewQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
