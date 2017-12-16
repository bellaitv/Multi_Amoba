package com.bellai.android.multi_amoba.activity.client;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.bellai.android.multi_amoba.activity.CommunicationInterface.IClientCommunication;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by adam.bellai on 2016. 12. 21..
 */
public class ClientThread extends AsyncTask<String, String, Object> implements IClientCommunication{

    private static final String TAG = ClientThread.class.getName();
    public static ClientThread me;

    private Socket socket;
    private Scanner scanner;
    private PrintStream outStream;
    private Activity activity;
    private String ip;
    private int port;
    private MSGReceiverThread mSGReceiverThread;


    public ClientThread(String ip, int port, Activity activity) throws IOException {
        this.ip = ip;
        this.port = port;
        this.activity = activity;
        me = this;
    }

    @Override
    public void sendMessage(String message) {
        com.bellai.android.multi_amoba.activity.client.MessageSender messageSender = new
                com.bellai.android.multi_amoba.activity.client.MessageSender(outStream);
        messageSender.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, message);
    }

    public String receiveMessage() {
        String result = new String();
        try {
            if (scanner.hasNextLine())
                result = result + scanner.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected Object doInBackground(String... strings) {
        Log.d(TAG, "doInBackground start");
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), 20000);
            Log.d(TAG, "connection has been created");
            scanner = new Scanner(socket.getInputStream());
            outStream = new PrintStream(socket.getOutputStream());
            startMessageReceiver();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d(TAG, "doInBackground finish");
        return null;
    }

    public void startMessageReceiver() {
        MessageReceiver receiver = new MessageReceiver(scanner, activity);
        receiver.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    public Scanner getScanner() {
        return this.scanner;
    }

    public Socket getSocket() {
        return socket;
    }

    public PrintStream getOutStream() {
        return outStream;
    }
}