package com.bellai.android.multi_amoba.activity.client;

import android.os.AsyncTask;

import java.io.PrintStream;

/**
 * Created by adam.bellai on 2016. 12. 26..
 */
public class MessageSender extends AsyncTask<String, String, Object> {

    private PrintStream outStream;

    public MessageSender(PrintStream outStream) {
        this.outStream = outStream;
    }
    @Override
    protected Object doInBackground(String... strings) {
        outStream.println(strings[0]);
        return null;
    }
}
