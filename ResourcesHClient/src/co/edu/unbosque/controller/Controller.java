package co.edu.unbosque.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;

import javax.swing.JOptionPane;

import co.edu.unbosque.view.Client;
import co.edu.unbosque.view.VMain;

public class Controller implements ActionListener {

	private VMain mainw;
	private Client client;

	public Controller() {
		mainw = new VMain();
		client = new Client("127.0.0.1", 5000);
		mainw.getBdelete().addActionListener(this);
		mainw.getBdelete().setActionCommand("erase");

		mainw.getBshowall().addActionListener(this);
		mainw.getBshowall().setActionCommand("show");

		mainw.getBregister().addActionListener(this);
		mainw.getBregister().setActionCommand("register");

		mainw.getBmodify().addActionListener(this);
		mainw.getBmodify().setActionCommand("modify");

		mainw.getBlist().addActionListener(this);
		mainw.getBlist().setActionCommand("list");

		mainw.setVisible(true);
	}

	public void start() {
		client.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("erase")) {
			if (mainw.verifyAction()) {
				client.send(mainw.eraseInfo());
				client.run();
				mainw.EmptyId();
			} else {
				JOptionPane.showMessageDialog(null, "ERROR: Rellena la casilla con la ID a usar.");
			}
		}

		if (e.getActionCommand().equals("show")) {
			if (mainw.verifyAction()) {
				client.send(mainw.showInfo());
				client.run();
				mainw.EmptyId();
				mainw.getInfo().setText(client.getAnswer());
			} else {
				JOptionPane.showMessageDialog(null, "ERROR: Rellena la casilla con la ID a usar.");
			}
		}

		if (e.getActionCommand().equals("list")) {
			client.send(mainw.showListInfo());
			client.run();
			mainw.getInfo().setText(client.getAnswer());

		}

		if (e.getActionCommand().equals("register")) {
			if (mainw.verifyRM()) {
				client.send(mainw.registerInfo());
				client.run();
				mainw.eraseData();
			} else {
				JOptionPane.showMessageDialog(null, "ERROR: Rellena las casillas con los datos a registrar.");
			}
		}

		if (e.getActionCommand().equals("modify")) {
			if (mainw.verifyAction() && mainw.verifyRM()) {
				client.send(mainw.modifyInfo());
				client.run();
				mainw.eraseData();
				mainw.EmptyId();
			} else {
				JOptionPane.showMessageDialog(null,
						"ERROR: Rellena la casilla con la ID a usar y los datos a modificar.");
			}
		}

	}

}
