package com.bellai.android.multi_amoba.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bellai.android.multi_amoba.R;
import com.bellai.android.multi_amoba.activity.client.ClientThread;

import java.io.IOException;

public class ConnectToSocketActivity extends BaseActivity {

    private EditText mEditTextIpAddress;
    private EditText mEditTextPort;
    private Button mButtonConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connecto_to_socket);
        initialize();
    }

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if ((mEditTextIpAddress.getText().toString().length() > 0) &&
                    (mEditTextPort.getText().toString().length() > 0)) {
                mButtonConnect.setEnabled(true);
            }
        }
    };


    @Override
    public void initialize() {
        initProgressbar();
        mEditTextIpAddress = (EditText) findViewById(R.id.editTextIpAddress);
        mEditTextIpAddress.addTextChangedListener(mTextWatcher);
        mEditTextPort = (EditText) findViewById(R.id.editTextPort);
        mEditTextPort.addTextChangedListener(mTextWatcher);
        mButtonConnect = (Button) findViewById(R.id.buttonConnect);
        mButtonConnect.setEnabled(false);
        mButtonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                innerOnClick();
            }
        });
    }

    public void innerOnClick() {
        boolean isDeviceOnline = isNetworkAvailable();
        if (isDeviceOnline) {
            connect();
            progressBar.setVisibility(View.VISIBLE);
        }
        else
            Toast.makeText(this, "No internet connection",
                    Toast.LENGTH_LONG).show();
    }

    private void connect() {
        try {
            ClientThread clientThread = new ClientThread(mEditTextIpAddress.getText().toString(),
                    Integer.parseInt(mEditTextPort.getText().toString()), this);
            clientThread.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            goToNewActivity(NewGameActivity.class, MainActivity.WIFI_MODE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
