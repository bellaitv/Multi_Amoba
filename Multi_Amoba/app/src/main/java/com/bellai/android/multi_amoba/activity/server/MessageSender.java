package com.bellai.android.multi_amoba.activity.server;

import android.os.AsyncTask;
import android.util.Log;

import java.io.PrintStream;
import java.util.List;

/**
 * Created by adam.bellai on 2016. 12. 25..
 */
public class MessageSender extends AsyncTask<String, String, Object> {

    private static final String TAG = MessageSender.class.getName();

    private List<Object> outStreams;

    public MessageSender(List<Object> outStreams){
        this.outStreams = outStreams;
    }

    @Override
    protected Object doInBackground(String... strings) {
        Log.d(TAG, "doInBackground start");
        for (Object printStream : outStreams) {
            PrintStream ps = (PrintStream) printStream;
            ps.println(strings[0]);
        }
        Log.d(TAG, "doInBackground finish");
        return null;
    }
}
