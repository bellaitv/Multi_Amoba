package com.bellai.android.multi_amoba.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bellai.android.multi_amoba.R;
import com.bellai.android.multi_amoba.activity.server.ServerThread;

public class CreateNewSocketActivity extends BaseActivity {

    private EditText meditTextNewPort;
    private Button mButtonCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_socket);
        initialize();
    }

    @Override
    public void initialize() {
        initProgressbar();
        meditTextNewPort = (EditText) findViewById(R.id.editTextNewPort);
        meditTextNewPort.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 0)
                    mButtonCreate.setEnabled(true);
            }
        });
        mButtonCreate = (Button) findViewById(R.id.buttonCreate);
        mButtonCreate.setEnabled(false);
        mButtonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                ServerThread serverThread = new ServerThread(meditTextNewPort.getText().toString());
                serverThread.execute();
                goToNewActivity(NewServerGameActivity.class, MainActivity.WIFI_MODE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }
}
