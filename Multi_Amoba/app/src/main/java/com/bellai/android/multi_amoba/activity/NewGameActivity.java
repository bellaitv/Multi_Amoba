package com.bellai.android.multi_amoba.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bellai.android.multi_amoba.R;
import com.bellai.android.multi_amoba.activity.CommunicationInterface.IClientCommunication;
import com.bellai.android.multi_amoba.activity.bluetooth.BluetoothClient;
import com.bellai.android.multi_amoba.activity.client.ClientThread;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class NewGameActivity extends BoardActivity {

    private static final String TAG = NewGameActivity.class.getName();

    private IClientCommunication mClientThread;
    public static NewGameActivity me;

    @Override
    public void innerOnClick(View view) {
        ImageView actual = (ImageView) view;
        /*String actualMsg = actual.getText().toString();
        if (actualMsg.isEmpty() || actualMsg.equals(BoardActivity.PLACE_HOLDER)) {*/
        Object tag = actual.getTag();
        if (tag == null) {
            if (enabled) {
                String msg = String.format("%d", view.getId());
                mClientThread.sendMessage(msg);
                actual.setImageResource(com.bellai.android.multi_amoba.activity.Icon.CLIENT);
               /* actual.setText(PlayerSigns.SERVER.toString());
                ((ImageView) view).setTextColor(Color.RED);*/
                actual.setTag(new IDObject(actual.getId(), com.bellai.android.multi_amoba.activity.Icon.CLIENT));
                check();
                enabled = false;
            }
        }
    }

    @Override
    public void check() {
        Log.d(TAG, "check start");
        int winnerCode = mWinningChecker.check(R.mipmap.newo);
        boolean endOfGame = false;
        String msg = new String("End of game");
        if(winnerCode == R.mipmap.newo) {
            msg = "You won";
            endOfGame = true;
        }
        else if(winnerCode == WinningChecker.DRAW) {
            msg = "You lost";
            endOfGame = true;
        }
        winnerCode = mWinningChecker.check(R.mipmap.newx);
        if(winnerCode == R.mipmap.newx) {
            msg = "You won";
            endOfGame = true;
        }
        else if(winnerCode == WinningChecker.DRAW) {
            msg = "You lost";
            endOfGame = true;
        }
        if (endOfGame) {
            makeDialog(msg);
            close();
        }
        Log.d(TAG, "check start");
    }

    @Override
    public void initialize() {
        super.initialize();
        NewGameActivity.me = this;
        Intent intent = getIntent();
        int mode = intent.getIntExtra(MainActivity.MODE, -1);
        if(mode == MainActivity.WIFI_MODE)
            mClientThread = ClientThread.me;
        else
            mClientThread = new BluetoothClient(this);
    }

    public void serverAction(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                innerServerAction(Integer.parseInt(msg));
            }
        });
        //enableBoard(true);
        enabled = true;
    }

    private void innerServerAction(int index) {
        for (int i = 0; i < board.length; i++) {
            ImageView[] actualRow = board[i];
            for (ImageView actual : actualRow) {
                if (actual.getId() == index) {
                    Object tag = actual.getTag();
                    /*if (actualMsg.isEmpty() || actualMsg.equals(BoardActivity.PLACE_HOLDER)) {*/
                    if (tag == null) {
                        actual.setImageResource(Icon.SERVER);
                        actual.setTag(new IDObject(actual.getId(), Icon.SERVER));
                        //actual.setText(PlayerSigns.SERVER.toString());
                        //actual.setTextColor(Color.RED);
                    }
                }
            }
        }
        check();
    }

    public void close() {
        try {
            ClientThread clientThread = ClientThread.me;
            Socket socket = clientThread.getSocket();
            socket.close();
            PrintStream printStream = clientThread.getOutStream();
            printStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
