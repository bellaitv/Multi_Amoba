package com.bellai.android.multi_amoba.activity.server;

import android.util.Log;

import java.util.Scanner;

public class MessageReceiver extends Receiver implements Runnable {

	private static final String TAG = MessageReceiver.class.getName();
	
	public MessageReceiver(Scanner scanner, Server server) {
		super(scanner, server);
	}

	@Override
	public void run() {
		Log.d(TAG, "run start");
		while (true) {
			try {
				if (getScanner().hasNextLine()) {
						String msg = getScanner().nextLine();
					Log.d(TAG, String.format("new msg %s", msg));
					getServer().clientAction(msg);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//logger.debug("Run Finish");
		}
	}

	public void clientActionFireEvent() {

	}

}
