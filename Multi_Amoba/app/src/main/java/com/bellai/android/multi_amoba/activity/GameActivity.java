package com.bellai.android.multi_amoba.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bellai.android.multi_amoba.R;
import com.bellai.android.multi_amoba.activity.bluetooth.NewBluetoothGameActivity;
import com.bellai.android.multi_amoba.activity.bluetooth.NewServerBluetoothGameActivity;

public class GameActivity extends BaseActivity {

    private TextView mtextViewCreateSocket;
    private TextView mtextViewConnectSocket;
    private TextView mTextViewMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_game);
        initialize();
    }

    @Override
    public void initialize() {
        mTextViewMode = (TextView) findViewById(R.id.textViewMode);
        Intent intent = getIntent();
        int mode = intent.getIntExtra(MainActivity.MODE, 1);
        mtextViewCreateSocket = (TextView) findViewById(R.id.textViewCreateSocket);
        mtextViewConnectSocket = (TextView) findViewById(R.id.textViewConnectSocket);
        if(mode == MainActivity.WIFI_MODE) {
            mTextViewMode.setText("Create wifi game");
            mtextViewCreateSocket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToNewActivity(CreateNewSocketActivity.class, MainActivity.WIFI_MODE);
                }
            });
            mtextViewConnectSocket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToNewActivity(ConnectToSocketActivity.class, MainActivity.WIFI_MODE);
                }
            });
        }else {
            mTextViewMode.setText("Create bluetooth game");
            mtextViewCreateSocket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToNewActivity(NewServerGameActivity.class, MainActivity.BLUETOOTH_MODE);
                }
            });
            mtextViewConnectSocket.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToNewActivity(NewGameActivity.class, MainActivity.BLUETOOTH_MODE);
                }
            });
        }
    }
}
