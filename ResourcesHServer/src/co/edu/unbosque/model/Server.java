/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unbosque.model;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author englinx
 */
public class Server extends Thread {
	// initialize socket and input stream
	private Socket socket; // This socket is for read client message
	private Socket socketR;// This socket is for send a message to client
	private ServerSocket server;
	private DataInputStream in;
	private DataOutputStream out;
	private int port;
	private String addressClient;
	private String line;

	// constructor with port
	public Server(int port) {
		// initialize socket and input stream
		this.socket = null;
		this.socketR = null;
		try {
			this.server = new ServerSocket(this.port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
		this.in = null;
		this.out = null;
		this.port = port;
		this.addressClient = addressClient;
		this.line = "";

	}

	@Override
	public void run() {

		try {
			this.server = new ServerSocket(this.port);
			System.out.println("Server started");

		} catch (IOException i) {
			System.out.println(i);
		}

	}

	public void readClient() {
		try {

			System.out.println("Waiting for a client ...");
			this.socket = server.accept();
			System.out.println("Client accepted");
			this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

			line = in.readUTF();
			// Print in server the client message
			System.out.println(line);
		} catch (IOException i) {
			System.out.println(i);
		}

	}

	public void sendClient(String message) {
		try {
			this.socketR = new Socket(this.socket.getInetAddress(), this.port + 1);
			// sends output to the socket to client
			this.out = new DataOutputStream(socketR.getOutputStream());
			this.out.writeUTF(message);
			this.out.close();

			this.in.close();
			this.server.close();
		} catch (IOException i) {
			System.out.println(i);
		}
	}

	public void closeServer() {
		System.out.println("Closing connection");

		// close connection
		try {
			socket.close();
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

}
