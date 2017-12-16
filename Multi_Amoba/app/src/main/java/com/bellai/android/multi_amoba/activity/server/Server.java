package com.bellai.android.multi_amoba.activity.server;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.bellai.android.multi_amoba.activity.CommunicationInterface.IServerCommunication;
import com.bellai.android.multi_amoba.activity.NewServerGameActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Created by adam.bellai on 2016. 12. 21..
 */
public class Server implements IServerCommunication{

    private static final String TAG = Server.class.getName();
    public static Server me;

    private ServerSocket server;
    private List<Object> outStreams;
    private List<Object> msgReceivers;
    private List<Object> inputStreams;
    private int port;
    private NewServerGameActivity mNewServerGameActivity;

    public Server(int port) throws IOException {
        me = this;
        this.port = port;
        outStreams = Collections.synchronizedList(new ArrayList<>());
        msgReceivers = Collections.synchronizedList(new ArrayList<>());
        inputStreams = Collections.synchronizedList(new ArrayList<>());
        server = new ServerSocket(port);
    }

    public void waitingForNewPlayer() throws IOException {
        Socket connection = server.accept();
        newPlayer();
        Scanner scanner = createNewPrintStreamAndScannerAndAddToList(connection);
        addNewHelperToListAndStartIt(scanner);
    }

    public void setNewServerGameActivity(NewServerGameActivity newServerGameActivity) {
        this.mNewServerGameActivity = newServerGameActivity;
        this.mNewServerGameActivity.setVisibleProgressBar();
    }

    private Scanner createNewPrintStreamAndScannerAndAddToList(Socket connection) throws IOException {
        InputStream inputStream = connection.getInputStream();
        OutputStream outputStream = connection.getOutputStream();
        inputStreams.add(inputStream);
        Scanner scanner = new Scanner(inputStream);
        PrintStream outStream = new PrintStream(outputStream);
        outStreams.add(outStream);
        return scanner;
    }

    private void addNewHelperToListAndStartIt(Scanner scanner) {
        MessageReceiver msgReceiver = new MessageReceiver(scanner, this);
        //FileReceiver fileReceiver = new FileReceiver(scanner, this);
        msgReceivers.add(msgReceiver);
        //fileReceivers.add(fileReceiver);
        Thread msgThreadt = new Thread(msgReceiver);
        msgThreadt.start();
        //Thread fileThread = new Thread(fileReceiver);
        //fileThread.start();
    }

    public void clientAction(String msg) {
        mNewServerGameActivity.clientAction(msg);
    }

    @Override
    public List<Object> getOutStreams() {
        return outStreams;
    }

    public void run() {
        Log.d(TAG, "run start");
        while (true) {
            Socket connection = null;
            try {
                connection = server.accept();
                newPlayer();
                Scanner scanner = createNewPrintStreamAndScannerAndAddToList(connection);
                addNewHelperToListAndStartIt(scanner);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void newPlayer() {
        mNewServerGameActivity.dismissProgressBar();
        mNewServerGameActivity.makeToast("New player has connected");
        Log.d(TAG, "new player");
    }

    public ServerSocket getServer() {
        return server;
    }
}
