/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.util.ArrayList;
import java.util.Random;
import java.rmi.*;
import java.rmi.server.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mikkel
 */
public class ServerLogic extends UnicastRemoteObject implements IKatServer {

	private static final SessionList CURRENT_USERS = new SessionList();
	private static final List<String> MESSAGES = new ArrayList<>();
	private static final IUserDataBase USERS = new UserDatabaseMap();

	public ServerLogic() throws RemoteException {
		USERS.addUser("Konstantin", "mypassword");
		USERS.addUser("Mikkel", "hispassword");
		USERS.addUser("demo", "password");
		USERS.addUser("Kim", "herspassword");
		USERS.addUser("Joakim", "ijdovbibszovhob120932349oi8yeSDVV!");
		USERS.addUser("Teacher", "qwerty");
		USERS.addUser("null", "");
	}

	@Override
	public int login(String name, String password) throws RemoteException {
		if (USERS.hasUser(name, password)) {
			return makeSessionId(name);
		} else {
			return -1;
		}
	}

	@Override
	public void logOut(int session) {
		CURRENT_USERS.remove(session);
	}

	@Override
	public ArrayList<String> getMessage(int session) throws RemoteException {
		SessionInfo info = CURRENT_USERS.getSession(session);
		if (info != null) {
			if (info.lastMessage == MESSAGES.size()) {
				synchronized (MESSAGES) {
					try {
						MESSAGES.wait();
					} catch (InterruptedException ex) {
					}
				}
			}
			// check that we haven't been logged out while we waited
			info = CURRENT_USERS.getSession(session);
			if (info == null) {
				ArrayList<String> returnList = new ArrayList<>();
				returnList.add("/Logged out");
				return returnList;
			}

			ArrayList<String> returnMessages = new ArrayList<>(
					MESSAGES.subList(info.lastMessage, MESSAGES.size()));
			info.lastMessage = MESSAGES.size();
			CURRENT_USERS.update(session, info);
			return returnMessages;
		}
		return null;
	}

	@Override
	public String sentMessage(String message, int session) throws RemoteException {
		System.out.println("Message recieved from:" + session + "\nMessage:" + message);
		SessionInfo info = CURRENT_USERS.getSession(session);

		if (info == null) {
			return "/illegal session";
		}

		if (message.equals("")) {
			return "";
		}

		Analyse am = new Analyse();
		String analyseM;
		try {
			analyseM = am.analyse(message, info.name);
		} catch (Exception ex) {
			String msg = "Der skete en uforudset fejl i java serveren:\n";
			msg += ex.getMessage();
			Logger.getLogger(ServerLogic.class.getName()).log(Level.SEVERE, null, ex);
			return msg;
		}

		if (!analyseM.equals("") && analyseM.charAt(0) == '/') {
			return analyseM.substring(1);
		}

		message = info.name + ": " + analyseM;
		synchronized (MESSAGES) {
			MESSAGES.add(message);
			MESSAGES.notifyAll();
		}
		return "";
	}

	private int makeSessionId(String username) {
		Random rand = new Random();

		int id;
		do {
			id = rand.nextInt();
		} while (id <= 0 || CURRENT_USERS.getSession(id) != null);

		CURRENT_USERS.put(username, id, MESSAGES.size());
		return id;
	}
}
