/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import server.IKatServer;

/**
 *
 * @author Mikkel
 */
public class ChatServerConnector {

	private static IKatServer connect() {
		IKatServer srv = null;
		try {
			srv = (IKatServer) Naming.lookup(IKatServer.FULL_ADDRESS);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return srv;
	}

	public static int login(String name, String password) {
		IKatServer srv = connect();
		try {
			return srv.login(name, password);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static String sentMessage(String message, int session) {
		IKatServer srv = connect();
		try {
			return srv.sentMessage(message, session);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
                return"";
	}
	
	public static ArrayList<String> getMessage(int session) {
		IKatServer srv = connect();
		try {
			return srv.getMessage(session);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	
}
