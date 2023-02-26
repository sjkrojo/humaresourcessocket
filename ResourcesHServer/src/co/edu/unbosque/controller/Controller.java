package co.edu.unbosque.controller;

import co.edu.unbosque.model.CandidateDAO;
import co.edu.unbosque.model.Server;

public class Controller {

	private Server server;
	private CandidateDAO can_dao;

	public Controller() {
		server = new Server(5000);
		can_dao = new CandidateDAO();
	}

	public void start() {
		server.start();
		while (!server.getLine().equals("Over")) {
			server.setLine("");
			server.readClient();

			String[] message = server.getLine().split(",");
			if (message[0].equals("erase")) {
				can_dao.erase(Integer.valueOf(message[1]));
				server.sendClient("Se ha eliminado");
			} else if (message[0].equals("modify")) {
				can_dao.modify(message[2], message[3], Integer.valueOf(message[5]), Integer.valueOf(message[3]),
						message[4], Integer.valueOf(message[1]));
				server.sendClient("Se ha modificado");
			} else if (message[0].equals("register")) {
				can_dao.register(can_dao.create(message[1], message[2], Integer.valueOf(message[5]),
						Integer.valueOf(message[3]), message[4]));
				server.sendClient("Se ha registrado");
			} else if (message[0].equals("show")) {
				server.sendClient(can_dao.showID(Integer.valueOf(message[1])).toString());
			} else if (message[0].equals("showlist")) {
				server.sendClient(can_dao.idlist());

			}

			if (!message[0].equals("") && (message[0].equals("showlist") || message[0].equals("show")
					|| message[0].equals("register") || message[0].equals("modify") || message[0].equals("erase"))) {
				server.run();
			}

		}
		server.closeServer();
	}

}
