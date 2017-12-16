package com.bellai.android.multi_amoba.activity.bluetooth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bellai.android.multi_amoba.R;
import com.bellai.android.multi_amoba.activity.BoardActivity;
import com.bellai.android.multi_amoba.activity.IDObject;
import com.bellai.android.multi_amoba.activity.WinningChecker;

public class NewBluetoothGameActivity extends BoardActivity {

    private BluetoothClient mClient;

    @Override
    public void initialize() {
        super.initialize();
        mClient = new BluetoothClient(this);
    }

    @Override
    public void innerOnClick(View view) {
        ImageView actual = (ImageView) view;
        Object tag = actual.getTag();
        if (tag == null) {
            if (enabled) {
                String msg = String.format("%d", view.getId());
                mClient.sendMessage(msg);
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
        int winnerCode = mWinningChecker.check(R.mipmap.newo);
        boolean endOfGame = false;
        String msg = new String("End of game");
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
        winnerCode = mWinningChecker.check(R.mipmap.newx);
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

    public void serverAction(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                innerServerAction(Integer.parseInt(msg));
            }
        });
    }

    private void innerServerAction(int index) {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board.length; j++)
                if (board[i][j].getId() == index) {
                    Object tag = board[i][j].getTag();
                    //String actualMsg = board[i][j].getText().toString();
                    /*if (actualMsg.isEmpty() || actualMsg.equals(BoardActivity.PLACE_HOLDER)) {*/
                    if (tag == null) {
                        board[i][j].setImageResource(com.bellai.android.multi_amoba.activity.Icon.SERVER);
                        board[i][j].setTag(new IDObject(board[i][j].getId(), com.bellai.android.multi_amoba.activity.Icon.SERVER));
                        //board[i][j].setTextColor(Color.BLUE);
                        check();
                    }
                }
    }

    @Override
    public void close() {
        //todo
    }
}
