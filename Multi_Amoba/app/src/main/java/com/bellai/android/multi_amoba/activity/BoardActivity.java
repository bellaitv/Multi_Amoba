package com.bellai.android.multi_amoba.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.bellai.android.multi_amoba.R;

/**
 * Created by adam.bellai on 2016. 12. 22..
 */
public abstract class BoardActivity extends AppCompatActivity {

    public static final int LENGTH = 40;
    private static final String COLOR = "COLOR";
    private static final String ENABLED_KEY = "ENABLED";
    private static final String TAG = BoardActivity.class.getName();
    protected boolean enabled = true;
    protected ProgressDialog progressbar;
    private Button buttonEndOfGame;

    private TableLayout tableLayoutBoard;
    protected ImageView[][] board;
    protected WinningChecker mWinningChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_game);
        initialize();
    }

    public void setVisibleProgressBar() {
        progressbar.show();
    }

    public void dismissProgressBar() {
        progressbar.dismiss();
    }

    public void initialize() {
        setButtonEndOfGame();
        initProgressBar();
        tableLayoutBoard = (TableLayout) findViewById(R.id.tableLayoutBoard);
        board = new ImageView[LENGTH][LENGTH];
        int id = 0;
        for (int i = 0; i < board.length; i++) {
            int color = (i % 2 == 0) ? Color.LTGRAY : Color.GRAY;
            TableRow tableRow = new TableRow(this);
            tableRow.setBackgroundColor(Color.WHITE);
            tableRow.setMinimumHeight(30);
            tableRow.setMinimumWidth(30);
            for (int j = 0; j < board.length; j++) {
                board[i][j] = new ImageView(this);
                board[i][j].setBackgroundColor(color);
                board[i][j].setId(id);
                board[i][j].setMinimumWidth(30);
                board[i][j].setMinimumHeight(30);
                board[i][j].setImageResource(Icon.PLACE_hOLDER);
                board[i][j].setBackgroundResource(R.drawable.board_background);
                board[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        innerOnClick(view);
                    }
                });
                tableRow.addView(board[i][j]);

                id++;
            }
            tableLayoutBoard.addView(tableRow);
        }
        mWinningChecker = new WinningChecker(board);
    }

    private void setButtonEndOfGame() {
        buttonEndOfGame = (Button) findViewById(R.id.buttonEndGame);
        buttonEndOfGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
                Intent intent = new Intent(BoardActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initProgressBar() {
        progressbar = new ProgressDialog(this);
        progressbar.setCancelable(false);
        progressbar.setMessage("Waiting for player...");
    }

    public abstract void innerOnClick(View view);

    public abstract void check();

    public abstract void close();

    public void makeToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                innerMakeToast(msg);
            }
        });
    }

    private void innerMakeToast(String msg) {
        Toast.makeText(this, msg,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        int index = 0;
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board.length; j++) {
                IDObject idObject = (IDObject) board[i][j].getTag();
                if (idObject != null) {
                    savedInstanceState.putInt(String.format("%d", index), idObject.getSign());
                    savedInstanceState.putInt(String.format("%s%d", COLOR, index), idObject.getId());
                } else {
                    savedInstanceState.putInt(String.format("%d", index), 0);
                    savedInstanceState.putInt(String.format("%s%d", COLOR, index), 0);
                }
                index++;
            }
        savedInstanceState.putBoolean(ENABLED_KEY, enabled);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int index = 0;
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == null) {
                    board[i][j] = new ImageView(this);
                    board[i][j].setImageResource(Icon.PLACE_hOLDER);
                }
                int sign = savedInstanceState.getInt(String.format("%d", index));
                int savedIndex = savedInstanceState.getInt(String.format("%s%d", COLOR, index));
                if (sign != 0)
                    board[i][j].setImageResource(sign);
                else
                    board[i][j].setImageResource(Icon.PLACE_hOLDER);
                board[i][j].setTag(new IDObject(savedIndex, sign));
                index++;
            }
        enabled = savedInstanceState.getBoolean(ENABLED_KEY);
    }

    private void innerEnableBoard(boolean value) {
        Log.d(TAG, String.format("innerEnableBoard START with %b", value));
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board.length; j++) {
                board[i][j].setEnabled(value);
            }
        enabled = value;
        Log.d(TAG, "innerEnableBoard FINISH");
    }

    protected void enableBoard(final boolean value) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                innerEnableBoard(value);
            }
        });
    }

    protected void makeDialog(String title) {
        DialogMaker dialogMaker = new DialogMaker(this, title, "Do you want to play new game?");
        dialogMaker.initialize();
        dialogMaker.show();
    }
}
