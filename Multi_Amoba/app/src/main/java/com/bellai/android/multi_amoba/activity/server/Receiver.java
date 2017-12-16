package com.bellai.android.multi_amoba.activity.server;

import java.util.Scanner;

public class Receiver {
	private Scanner scanner;
	private Server server;
	
	public Receiver(Scanner scanner, Server server) {
		this.scanner = scanner;
		this.server = server;
	}
	
	public Scanner getScanner() {
		return scanner;
	}

	public void setScanner(Scanner scanner) {
		this.scanner = scanner;
	}

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}
}
