package com.bellai.android.multi_amoba.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bellai.android.multi_amoba.R;
import com.bellai.android.multi_amoba.activity.CommunicationInterface.IServerCommunication;
import com.bellai.android.multi_amoba.activity.bluetooth.BluetoothServer;
import com.bellai.android.multi_amoba.activity.server.MessageSender;
import com.bellai.android.multi_amoba.activity.server.Server;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by adam.bellai on 2016. 12. 22..
 */
public class NewServerGameActivity extends BoardActivity {

    private static final String TAG = NewServerGameActivity.class.getName();

    private IServerCommunication mServer;

    public void initialize() {
        super.initialize();
        Intent intent = getIntent();
        int mode = intent.getIntExtra(MainActivity.MODE, -1);
        if (mode == MainActivity.WIFI_MODE)
            mServer = Server.me;
        else
            mServer = new BluetoothServer(this);
        mServer.setNewServerGameActivity(this);
    }

    public void innerOnClick(View view) {
        ImageView actual = (ImageView) view;
        /*String actualMsg = actual.getText().toString();
        if (actualMsg.isEmpty() || actualMsg.equals(BoardActivity.PLACE_HOLDER)) {*/
        Object object = actual.getTag();
        if (object == null) {
            if (enabled) {
                //enableBoard(false);
                String msg = String.format("%d", view.getId());
                innerServerAction(Integer.parseInt(msg));
                try {
                    MessageSender messageSender = new MessageSender(mServer.getOutStreams());
                    messageSender.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                enabled = false;
            }
        }
    }

    @Override
    public void check() {
        Log.d(TAG, "check start");
        boolean endOfGame = false;
        String msg = new String("End of game");
        //int winnerCode = mWinningChecker.check(com.bellai.android.multi_amoba.activity.Icon.SERVER);
        int winnerCode = mWinningChecker.check(R.mipmap.newo);
        /*boolean endOfGame = false;
        String msg = new String("End of game");*/
        if(winnerCode == R.mipmap.newo) {
            msg = "You lost";
            endOfGame = true;
        }
        else if(winnerCode == WinningChecker.DRAW) {
            msg = "You won";
            endOfGame = true;
        }
        winnerCode = mWinningChecker.check(R.mipmap.newx);
        if(winnerCode == R.mipmap.newx) {
            msg = "You lost";
            endOfGame = true;
        }
        else if(winnerCode == WinningChecker.DRAW) {
            msg = "You won";
            endOfGame = true;
        }
        /*if (winnerCode != WinningChecker.CONTINUE) {
            switch (winnerCode) {
                case WinningChecker.CLIENT_WINS: {
                    msg = "You won.";
                    break;
                }
                case WinningChecker.SERVER_WINS: {
                    msg = " You lost.";
                    break;
                }
                case WinningChecker.DRAW: {
                    msg = "Draw.";
                    break;
                }
                default: {
                    msg = String.format("something wrong %d", winnerCode);
                }
            }
            endOfGame = true;
        }
        winnerCode = mWinningChecker.check(Icon.CLIENT);
        if (winnerCode != WinningChecker.CONTINUE) {
            switch (winnerCode) {
                case WinningChecker.CLIENT_WINS: {
                    msg = "You lost.";
                    break;
                }
                case WinningChecker.SERVER_WINS: {
                    msg = " You won.";
                    break;
                }
                case WinningChecker.DRAW: {
                    msg = "Draw.";
                    break;
                }
                default: {
                    msg = String.format("something wrong %d", winnerCode);
                }
            }
            endOfGame = true;
        }*/
        if (endOfGame) {
            try {
                makeDialog(msg);
                Server.me.getServer().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "check finish");
    }

    public void clientAction(String msg) {
        final int index = Integer.parseInt(msg);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                innerClientAction(index);
            }
        });
        //enableBoard(true);
        enabled = true;
    }

    public void innerClientAction(int index) {
        for (int i = 0; i < board.length; i++) {
            ImageView[] actualRow = board[i];
            for (ImageView actual : actualRow) {
                if (actual.getId() == index) {
                    Object tag = actual.getTag();
                    /*if (actualMsg.isEmpty() || actualMsg.equals(BoardActivity.PLACE_HOLDER)) {*/
                    if (tag == null) {
                        actual.setImageResource(Icon.CLIENT);
                        actual.setTag(new IDObject(actual.getId(), Icon.CLIENT));
                        //actual.setText(PlayerSigns.SERVER.toString());
                        //actual.setTextColor(Color.RED);
                    }
                }
            }
        }
        check();
    }

    private void innerServerAction(int index) {
        for (int i = 0; i < board.length; i++) {
            ImageView[] actualRow = board[i];
            for (ImageView actual : actualRow) {
                if (actual.getId() == index) {
                    Object tag = actual.getTag();
                    if (tag == null) {
                        actual.setImageResource(Icon.SERVER);
                        actual.setTag(new IDObject(actual.getId(), Icon.SERVER));
                        check();
                    }
                }
            }
        }
    }

    @Override
    public void finish() {
        close();
    }

    @Override
    public void close() {
        try {
            ServerSocket socket = Server.me.getServer();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}