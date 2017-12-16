package com.bellai.android.multi_amoba.activity.server;

import android.os.AsyncTask;

import java.io.IOException;

/**
 * Created by adam.bellai on 2016. 12. 21..
 */
public class ServerThread extends AsyncTask<String, String, Server> {

    private String port;

    public ServerThread(String port) {
        this.port = port;
    }

    @Override
    protected Server doInBackground(String... strings) {
        try {
            Server server = new Server(Integer.parseInt(port));
            server.waitingForNewPlayer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
