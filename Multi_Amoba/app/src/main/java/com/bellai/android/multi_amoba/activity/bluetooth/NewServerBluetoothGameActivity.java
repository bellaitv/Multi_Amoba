package com.bellai.android.multi_amoba.activity.bluetooth;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bellai.android.multi_amoba.R;
import com.bellai.android.multi_amoba.activity.BoardActivity;
import com.bellai.android.multi_amoba.activity.IDObject;
import com.bellai.android.multi_amoba.activity.Icon;
import com.bellai.android.multi_amoba.activity.WinningChecker;
import com.bellai.android.multi_amoba.activity.server.MessageSender;
import com.bellai.android.multi_amoba.activity.server.Server;

import java.io.IOException;

/**
 * Created by adam.bellai on 2017. 01. 22..
 */

public class NewServerBluetoothGameActivity extends BoardActivity {

    private BluetoothServer mServer;

    @Override
    public void initialize() {
        super.initialize();
        mServer = new BluetoothServer(this);
    }

    @Override
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
        boolean endOfGame = false;
        String msg = new String("End of game");
        int winnerCode = mWinningChecker.check(com.bellai.android.multi_amoba.activity.Icon.SERVER);
        if (winnerCode != WinningChecker.CONTINUE) {
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
        }
        if (endOfGame) {
            makeDialog(msg);
            close();
        }
    }

    @Override
    public void close() {
        try {
            Server.me.getServer().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

}
