package com.bellai.android.multi_amoba.activity.client;

import android.os.AsyncTask;

import java.util.Scanner;

/**
 * Created by adam.bellai on 2016. 11. 17..
 */
public class MSGReceiverThread extends AsyncTask<String, String, String> {

    private Scanner scanner;
    private ClientThread clientThread;

    public MSGReceiverThread(ClientThread clientThread) {
        this.clientThread = clientThread;
        setScannerIfNull();
    }

    @Override
    protected String doInBackground(String... strings) {
        valami();
        return null;
    }

    public void valami() {
        while (true) {
            String result = new String();
            try {
                if (scanner == null)
                    setScannerIfNull();
                if (scanner.hasNext()) {
                    result = result + scanner.nextLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void setScannerIfNull() {
        if (scanner == null) {
            scanner = clientThread.getScanner();
        }
    }
}
