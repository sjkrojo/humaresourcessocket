/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unbosque.view;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 *
 * @author englinx
 */
public class Client extends Thread {
	// initialize socket and input output streams
	private Socket socket;
	private ServerSocket server;
	private Scanner sn;
	private DataOutputStream out;
	private DataInputStream in; // Input stream from server
	private String address;
	private int port;
	private String line;
	private String answer;

	// constructor to put ip address and port
	public Client(String address, int port) {
		this.socket = null;
		this.server = null;
		this.sn = new Scanner(System.in);
		this.out = null;
		this.address = address;
		this.port = port;
		this.line = "";

	}

	public void run() {
		try {
			this.socket = new Socket(this.address, this.port);
			System.out.println("Connected");

		} catch (IOException i) {
			System.out.println(i);
		}

	}

	public void send(String message) {
		try {
			// sends output to the socket
			this.out = new DataOutputStream(socket.getOutputStream());

			// line = this.input.readLine();
			this.line = message;
			this.out.writeUTF(line);
			// close socket and output stream
			this.out.close();
			this.socket.close();
			// Create a serverSocket to wait message from server
			this.server = new ServerSocket(this.port + 1);
			this.socket = server.accept();
			System.out.println("Received message:");
			// takes input from the client socket
			this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			// Print in server the client message
			answer = in.readUTF();
			System.out.println(answer);
			this.in.close();
			this.server.close();

		} catch (IOException i) {
			System.out.println(i);
		}

		// close the connection
		try {
			out.close();
			socket.close();
		} catch (IOException i) {
			System.out.println(i);
		}
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

}
