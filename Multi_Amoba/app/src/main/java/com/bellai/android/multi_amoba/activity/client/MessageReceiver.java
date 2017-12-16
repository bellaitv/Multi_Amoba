package com.bellai.android.multi_amoba.activity.client;

import android.app.Activity;
import android.os.AsyncTask;

import com.bellai.android.multi_amoba.activity.NewGameActivity;

import java.util.Scanner;

/**
 * Created by adam.bellai on 2016. 12. 25..
 */
public class MessageReceiver extends AsyncTask<String, String, Object> {

    private Scanner scanner;
    private NewGameActivity mActivity;

    public MessageReceiver(Scanner scanner, Activity mActivity) {
        this.scanner = scanner;
        this.mActivity = NewGameActivity.me;
    }

    @Override
    protected Object doInBackground(String... strings) {
        String result;
        while (true)
            try {
                if (scanner.hasNextLine()) {
                    result = scanner.nextLine();
                    if(mActivity == null)
                        mActivity = NewGameActivity.me;
                    mActivity.serverAction(result);
                    if (false)
                        return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        // return result;
    }
}
